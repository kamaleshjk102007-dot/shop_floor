package com.example.assistant

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

data class AssistantInterpretation(
    val type: String,
    val fields: JSONObject,
    val answer: String = ""
)

object AssistantAiClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .build()

    fun interpretWithGemini(apiKey: String, command: String, liveData: String): AssistantInterpretation {
        val prompt = """
 You are the complete ShopFloor copilot for a factory supervisor. Read spoken or typed requests in any Indian language, English, or code-mixed speech. Silently understand/translate the request, preserve names, IDs, dates and numbers, and answer in the same language as the user whenever practical.
Return ONLY one raw JSON object. Never use markdown.

Allowed shapes:
{"type":"order","code":"","customer":"","desc":"","phrs":"","budget":"","start":"","end":"","status":""}
{"type":"editorder","soId":"","customer":"","desc":"","phrs":"","budget":"","start":"","end":"","status":""}
{"type":"emp","empId":"","name":"","dept":"","cat":"","skill":"","status":"","soId":""}
{"type":"assign","soId":"","empId":"","phrs":"","start":"","end":"","desc":"","status":""}
{"type":"editassign","soId":"","empId":"","phrs":"","start":"","end":"","desc":"","status":""}
{"type":"shiftassign","empId":"","fromSoId":"","toSoId":""}
{"type":"startwork","soId":"","empIds":[]}
{"type":"stopwork","soId":""}
{"type":"category","code":"","name":"","rate":""}
{"type":"editcategory","code":"","name":"","rate":""}
{"type":"department","code":"","name":"","desc":""}
{"type":"editdepartment","code":"","name":"","desc":""}
{"type":"editemp","empId":"","name":"","dept":"","cat":"","skill":"","status":"","soId":""}
{"type":"deleteorder","soId":""}
{"type":"deleteemp","empId":""}
{"type":"deletedepartment","code":""}
{"type":"deletecategory","code":""}
{"type":"updatehours","empId":"","hours":""}
{"type":"updateprogress","soId":"","qty":"","status":""}
{"type":"answer","text":"short factual answer based only on live data"}
{"type":"unknown"}

Rules:
- assign/put/add a worker to an order -> assign. Changing an existing worker assignment -> editassign.
- start/begin/clock in/start timer -> startwork. stop/end/clock out/pause/save hours -> stopwork.
- move/shift/transfer between orders -> shiftassign.
- Questions, counts, lookups and comparisons -> answer; never mutate for a question.
- Requests to change an employee -> editemp. Delete/remove requests -> the matching delete type.
- Manual actual-hours correction -> updatehours. Completed quantity/progress changes -> updateprogress.
- Convert spoken numeric words, including lakh, into plain digits. Dates must be YYYY-MM-DD.
- Unmentioned values stay empty. Statuses must use the app values shown in live data.
- For existing entities, output exact IDs from live data. Never invent an employee or order ID except for genuinely new emp/order actions.
- For employee create/edit, dept and cat must resolve to existing department/category values from live data. Never invent them and never silently choose one. Leave unmatched values as spoken so the app can reject them clearly.
- If startwork names no employees, empIds is empty, meaning everyone currently assigned to that order.
- For app questions, explain where/how to perform the task using Home, Master, Supervisor and Report.
- For operational questions, use live data to identify delays, overload, idle labour, budget/hour risk and practical next actions.
- You may give concise general shop-floor advice when live data is insufficient, but clearly distinguish general advice from facts found in LIVE APP DATA.
- Never claim that a record exists unless it appears in live data. Never invent measurements, costs, hours or IDs.

LIVE APP DATA:
$liveData
        """.trimIndent()
        val body = JSONObject()
            .put("systemInstruction", JSONObject().put("parts", JSONArray().put(JSONObject().put("text", prompt))))
            .put("contents", JSONArray().put(JSONObject().put("parts", JSONArray().put(JSONObject().put("text", command)))))
            .put(
                "generationConfig",
                JSONObject()
                    .put("temperature", 0)
                    .put("topP", 0.1)
                    .put("candidateCount", 1)
                    .put("maxOutputTokens", 512)
                    .put("responseMimeType", "application/json")
            )
            .toString().toRequestBody("application/json".toMediaType())
        val models = listOf("gemini-flash-lite-latest", "gemini-2.5-flash", "gemini-flash-latest")
        var lastError = "Gemini request failed"
        for (model in models) {
            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent")
                .header("x-goog-api-key", apiKey.trim())
                .post(body)
                .build()
            client.newCall(request).execute().use { response ->
                val text = response.body?.string().orEmpty()
                if (response.isSuccessful) {
                    val raw = JSONObject(text).getJSONArray("candidates").getJSONObject(0)
                        .getJSONObject("content").getJSONArray("parts").getJSONObject(0)
                        .getString("text").trim()
                        .replace("```json", "").replace("```", "").trim()
                    val parsed = JSONObject(raw)
                    return AssistantInterpretation(parsed.optString("type", "unknown"), parsed, parsed.optString("text"))
                }
                lastError = runCatching {
                    JSONObject(text).getJSONObject("error").optString("message")
                }.getOrDefault("Gemini request failed (${response.code})")
                if (response.code != 404) break
            }
        }
        error(lastError)
    }

    fun transcribeWithSarvam(apiKey: String, audioFile: File): String {
        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file", audioFile.name, audioFile.asRequestBody("audio/mp4".toMediaType()))
            .addFormDataPart("model", "saaras:v3")
            .addFormDataPart("mode", "codemix")
            .addFormDataPart("language_code", "unknown")
            .build()
        val request = Request.Builder()
            .url("https://api.sarvam.ai/speech-to-text")
            .header("api-subscription-key", apiKey)
            .post(multipart)
            .build()
        client.newCall(request).execute().use { response ->
            val text = response.body?.string().orEmpty()
            if (!response.isSuccessful) error("Sarvam request failed (${response.code})")
            return JSONObject(text).getString("transcript").trim()
        }
    }
}
