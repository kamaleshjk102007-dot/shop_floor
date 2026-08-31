package com.example.screens

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text as MaterialText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/** Single language source used by every screen-level text element. */
internal object UiLanguageRuntime {
    var tag by mutableStateOf("en")
}

internal fun localizedUiText(text: String): String {
    if (text == "OCS SHOPFLOOR" || text == "ShopFloor Tracking") return text
    val exactOrTemplate = AutoTranslations.translate(UiLanguageRuntime.tag, text)
    if (exactOrTemplate != text) return exactOrTemplate
    val replacements = when (UiLanguageRuntime.tag) {
        "ta" -> listOf(
            "Department Assignment" to "துறை ஒதுக்கீடு", "Manufactured Item" to "உற்பத்திப் பொருள்",
            "Planned Budget" to "திட்டமிட்ட பட்ஜெட்", "Planned Target" to "திட்டமிட்ட இலக்கு",
            "Newest First" to "புதியவை முதலில்", "Sales Orders" to "விற்பனை ஆர்டர்கள்",
            "Customer" to "வாடிக்கையாளர்", "Employees" to "பணியாளர்கள்", "Operators" to "இயக்குநர்கள்",
            "Completed" to "முடிந்தது", "Paused" to "இடைநிறுத்தப்பட்டது", "Running" to "இயங்குகிறது",
            "Project" to "திட்டம்", "Department" to "துறை", "Dept" to "துறை", "Cat" to "வகை",
            "Planned" to "திட்டமிடப்பட்டது", "Sort" to "வரிசை", "Export" to "ஏற்றுமதி",
            "Print PDF" to "PDF அச்சிடுக", "units" to "அலகுகள்", "hrs" to "மணி", "All" to "அனைத்தும்"
        )
        "hi" -> listOf(
            "Department Assignment" to "विभाग आवंटन", "Manufactured Item" to "निर्मित वस्तु", "Planned Budget" to "नियोजित बजट", "Planned Target" to "नियोजित लक्ष्य",
            "Newest First" to "नवीनतम पहले", "Sales Orders" to "बिक्री ऑर्डर", "Customer" to "ग्राहक", "Employees" to "कर्मचारी", "Operators" to "ऑपरेटर",
            "Completed" to "पूर्ण", "Paused" to "विरामित", "Running" to "चल रहा", "Project" to "परियोजना", "Department" to "विभाग", "Dept" to "विभाग", "Cat" to "श्रेणी",
            "Planned" to "नियोजित", "Sort" to "क्रम", "Export" to "निर्यात", "Print PDF" to "PDF प्रिंट करें", "units" to "इकाइयाँ", "hrs" to "घंटे", "All" to "सभी"
        )
        "te" -> listOf(
            "Department Assignment" to "విభాగ కేటాయింపు", "Manufactured Item" to "తయారైన వస్తువు", "Planned Budget" to "ప్రణాళిక బడ్జెట్", "Planned Target" to "ప్రణాళిక లక్ష్యం",
            "Newest First" to "కొత్తవి ముందు", "Sales Orders" to "సేల్స్ ఆర్డర్లు", "Customer" to "కస్టమర్", "Employees" to "ఉద్యోగులు", "Operators" to "ఆపరేటర్లు",
            "Completed" to "పూర్తయింది", "Paused" to "నిలిపివేయబడింది", "Running" to "నడుస్తోంది", "Project" to "ప్రాజెక్ట్", "Department" to "విభాగం", "Dept" to "విభాగం", "Cat" to "వర్గం",
            "Planned" to "ప్రణాళిక", "Sort" to "క్రమం", "Export" to "ఎగుమతి", "Print PDF" to "PDF ముద్రించండి", "units" to "యూనిట్లు", "hrs" to "గంటలు", "All" to "అన్నీ"
        )
        "kn" -> listOf(
            "Department Assignment" to "ವಿಭಾಗ ನಿಯೋಜನೆ", "Manufactured Item" to "ತಯಾರಿಸಿದ ವಸ್ತು", "Planned Budget" to "ಯೋಜಿತ ಬಜೆಟ್", "Planned Target" to "ಯೋಜಿತ ಗುರಿ",
            "Newest First" to "ಹೊಸದು ಮೊದಲು", "Sales Orders" to "ಮಾರಾಟ ಆರ್ಡರ್‌ಗಳು", "Customer" to "ಗ್ರಾಹಕ", "Employees" to "ಉದ್ಯೋಗಿಗಳು", "Operators" to "ಆಪರೇಟರ್‌ಗಳು",
            "Completed" to "ಪೂರ್ಣಗೊಂಡಿದೆ", "Paused" to "ವಿರಾಮಗೊಳಿಸಲಾಗಿದೆ", "Running" to "ಚಾಲನೆಯಲ್ಲಿದೆ", "Project" to "ಯೋಜನೆ", "Department" to "ವಿಭಾಗ", "Dept" to "ವಿಭಾಗ", "Cat" to "ವರ್ಗ",
            "Planned" to "ಯೋಜಿತ", "Sort" to "ಕ್ರಮ", "Export" to "ರಫ್ತು", "Print PDF" to "PDF ಮುದ್ರಿಸಿ", "units" to "ಘಟಕಗಳು", "hrs" to "ಗಂಟೆಗಳು", "All" to "ಎಲ್ಲಾ"
        )
        "ml" -> listOf(
            "Department Assignment" to "വകുപ്പ് നിയമനം", "Manufactured Item" to "നിർമ്മിച്ച ഇനം", "Planned Budget" to "ആസൂത്രിത ബജറ്റ്", "Planned Target" to "ആസൂത്രിത ലക്ഷ്യം",
            "Newest First" to "പുതിയത് ആദ്യം", "Sales Orders" to "സെയിൽസ് ഓർഡറുകൾ", "Customer" to "ഉപഭോക്താവ്", "Employees" to "ജീവനക്കാർ", "Operators" to "ഓപ്പറേറ്റർമാർ",
            "Completed" to "പൂർത്തിയായി", "Paused" to "താൽക്കാലികമായി നിർത്തി", "Running" to "പ്രവർത്തിക്കുന്നു", "Project" to "പദ്ധതി", "Department" to "വകുപ്പ്", "Dept" to "വകുപ്പ്", "Cat" to "വിഭാഗം",
            "Planned" to "ആസൂത്രിതം", "Sort" to "ക്രമം", "Export" to "കയറ്റുമതി", "Print PDF" to "PDF അച്ചടിക്കുക", "units" to "യൂണിറ്റുകൾ", "hrs" to "മണിക്കൂർ", "All" to "എല്ലാം"
        )
        else -> emptyList()
    }
    return replacements.sortedByDescending { it.first.length }.fold(text) { result, (source, target) ->
        result.replace(Regex("(?<![A-Za-z])${Regex.escape(source)}(?![A-Za-z])", RegexOption.IGNORE_CASE), target)
    }
}

/**
 * Package-wide Material text wrapper. All fixed UI copy passes through the
 * generated offline translations; saved business values are returned unchanged.
 */
@Composable
internal fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val localized = localizedUiText(text)
    val singleLine = maxLines == 1
    val translatedCopy = UiLanguageRuntime.tag != "en" && localized.any(Char::isLetter)
    val expansion = localized.length.toFloat() / text.length.coerceAtLeast(1)
    val scale = when {
        !translatedCopy -> 1f
        singleLine && expansion >= 1.65f -> 0.74f
        singleLine && expansion >= 1.30f -> 0.82f
        singleLine -> 0.90f
        expansion >= 1.75f -> 0.82f
        expansion >= 1.35f -> 0.88f
        else -> 0.94f
    }
    val adaptiveFontSize = if (fontSize != TextUnit.Unspecified && scale < 1f) {
        (fontSize.value * scale).coerceAtLeast(8f).sp
    } else fontSize
    val adaptiveOverflow = if (singleLine && overflow == TextOverflow.Clip) TextOverflow.Ellipsis else overflow

    MaterialText(
        text = localized, modifier = modifier, color = color, fontSize = adaptiveFontSize,
        fontStyle = fontStyle, fontWeight = fontWeight, fontFamily = fontFamily,
        letterSpacing = letterSpacing, textDecoration = textDecoration, textAlign = textAlign,
        lineHeight = lineHeight, overflow = adaptiveOverflow, softWrap = softWrap, maxLines = maxLines,
        minLines = minLines, onTextLayout = onTextLayout, style = style
    )
}
