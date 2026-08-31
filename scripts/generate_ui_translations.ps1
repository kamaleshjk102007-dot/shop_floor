param([string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot))

$screenRoot = Join-Path $ProjectRoot 'app\src\main\java\com\example\screens'
$sourceFiles = Get-ChildItem $screenRoot -Filter '*.kt' | Where-Object { $_.Name -ne 'AutoTranslations.kt' }
$phraseSet = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::Ordinal)
$literalPattern = [regex]'"((?:\\.|[^"\\])*)"'

foreach ($file in $sourceFiles) {
    $content = Get-Content -Raw -LiteralPath $file.FullName
    foreach ($match in $literalPattern.Matches($content)) {
        $value = $match.Groups[1].Value
        $value = $value.Replace('\"', '"').Replace('\n', "`n")
        if ($value -match '[A-Za-z]{2}' -and $value.Length -le 240 -and
            $value -notmatch '^(https?://|[A-Z_]+$|#[0-9A-Fa-f]+$|[a-z]+\.[a-z])' -and
            $value -notmatch '^com\.' -and $value -notmatch '^application/') {
            [void]$phraseSet.Add($value)
        }
    }
}

$phrases = @($phraseSet | Sort-Object)
$languages = [ordered]@{ ta = 'Tamil'; hi = 'Hindi'; te = 'Telugu'; kn = 'Kannada'; ml = 'Malayalam' }
$allTranslations = @{}
Add-Type -AssemblyName System.Net.Http
$httpClient = [System.Net.Http.HttpClient]::new()

foreach ($tag in $languages.Keys) {
    $translated = @{}
    for ($offset = 0; $offset -lt $phrases.Count; $offset += 18) {
        $end = [Math]::Min($offset + 17, $phrases.Count - 1)
        $batch = for ($index = $offset; $index -le $end; $index++) {
            "[[[$index]]] $($phrases[$index])"
        }
        $query = [Uri]::EscapeDataString(($batch -join "`n"))
        $url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=$tag&dt=t&q=$query"
        $responseBytes = $httpClient.GetByteArrayAsync($url).GetAwaiter().GetResult()
        $responseJson = [System.Text.Encoding]::UTF8.GetString($responseBytes)
        $response = ConvertFrom-Json $responseJson
        $joined = ($response[0] | ForEach-Object { $_[0] }) -join ''
        $matches = [regex]::Matches($joined, '(?ms)^\[\[\[(\d+)\]\]\]\s*(.*?)(?=^\[\[\[\d+\]\]\]|\z)')
        foreach ($item in $matches) {
            $index = [int]$item.Groups[1].Value
            $translated[$phrases[$index]] = $item.Groups[2].Value.Trim()
        }
        Start-Sleep -Milliseconds 100
    }
    $allTranslations[$tag] = $translated
}

function KotlinString([string]$value) {
    return $value.Replace('\', '\\').Replace('"', '\"').Replace('$', '\$').Replace("`r", '').Replace("`n", '\n')
}

$builder = [System.Text.StringBuilder]::new()
[void]$builder.AppendLine('package com.example.screens')
[void]$builder.AppendLine()
[void]$builder.AppendLine('// Generated static translations. Re-run scripts/generate_ui_translations.ps1 after adding UI copy.')
[void]$builder.AppendLine('internal object AutoTranslations {')
[void]$builder.AppendLine('    private val translations: Map<String, Map<String, String>> = mapOf(')
foreach ($tag in $languages.Keys) {
    [void]$builder.AppendLine("        `"$tag`" to mapOf(")
    foreach ($phrase in $phrases) {
        $translatedValue = $allTranslations[$tag][$phrase]
        if (-not [string]::IsNullOrWhiteSpace($translatedValue) -and $translatedValue -ne $phrase) {
            [void]$builder.AppendLine("            `"$(KotlinString $phrase)`" to `"$(KotlinString $translatedValue)`",")
        }
    }
    [void]$builder.AppendLine('        ),')
}
[void]$builder.AppendLine('    )')
[void]$builder.AppendLine('    private data class TemplateTranslation(val regex: Regex, val translated: String)')
[void]$builder.AppendLine('    private val placeholder = Regex("\\$\\{[^}]+\\}|\\$[A-Za-z_][A-Za-z0-9_]*")')
[void]$builder.AppendLine('    private val templates: Map<String, List<TemplateTranslation>> by lazy {')
[void]$builder.AppendLine('        translations.mapValues { (_, dictionary) -> dictionary.entries.filter { placeholder.containsMatchIn(it.key) }.map { (source, translated) ->')
[void]$builder.AppendLine('            val pattern = StringBuilder("^"); var cursor = 0')
[void]$builder.AppendLine('            placeholder.findAll(source).forEach { match -> pattern.append(Regex.escape(source.substring(cursor, match.range.first))).append("(.*?)"); cursor = match.range.last + 1 }')
[void]$builder.AppendLine('            pattern.append(Regex.escape(source.substring(cursor))).append(''\$''); TemplateTranslation(Regex(pattern.toString()), translated)')
[void]$builder.AppendLine('        } }')
[void]$builder.AppendLine('    }')
[void]$builder.AppendLine('    fun translate(languageTag: String, text: String): String {')
[void]$builder.AppendLine('        val dictionary = translations[languageTag] ?: return text; dictionary[text]?.let { return it }')
[void]$builder.AppendLine('        templates[languageTag].orEmpty().forEach { template -> val match = template.regex.matchEntire(text) ?: return@forEach; var i = 1; return placeholder.replace(template.translated) { match.groupValues.getOrElse(i++) { "" } } }')
[void]$builder.AppendLine('        return text')
[void]$builder.AppendLine('    }')
[void]$builder.AppendLine('}')

$destination = Join-Path $screenRoot 'AutoTranslations.kt'
[System.IO.File]::WriteAllText($destination, $builder.ToString(), [System.Text.UTF8Encoding]::new($false))
$httpClient.Dispose()
Write-Output "Generated $destination with $($phrases.Count) source phrases in $($languages.Count) languages."
