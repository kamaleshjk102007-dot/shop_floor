package com.example.data

import com.example.BuildConfig
import com.example.notifications.ThresholdAlert
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import org.json.JSONObject

class CloudSyncClient {
    private val baseUrl = BuildConfig.MONGODB_SYNC_BASE_URL.trim().trimEnd('/')
    private val token = BuildConfig.MONGODB_SYNC_TOKEN.trim()
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val isConfigured: Boolean get() =
        (baseUrl.startsWith("https://") || (BuildConfig.DEBUG && baseUrl.startsWith("http://"))) && token.isNotBlank()

    fun pushSnapshot(snapshot: String): Boolean {
        if (!isConfigured) return false
        val request = Request.Builder()
            .url("$baseUrl/api/v1/snapshot")
            .header("Authorization", "Bearer $token")
            .put(snapshot.toRequestBody(JSON))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) error("Cloud sync failed (${response.code})")
            return true
        }
    }

    fun pullSnapshot(): String? {
        if (!isConfigured) return null
        val request = Request.Builder()
            .url("$baseUrl/api/v1/snapshot")
            .header("Authorization", "Bearer $token")
            .get()
            .build()
        client.newCall(request).execute().use { response ->
            if (response.code == 404) return null
            if (!response.isSuccessful) error("Cloud pull failed (${response.code})")
            return response.body?.string()
        }
    }

    fun sendThresholdAlert(alert: ThresholdAlert): Boolean {
        if (!isConfigured) return false
        val payload = JSONObject().apply {
            put("deliveryId", alert.deliveryId)
            put("orderId", alert.orderId)
            put("alertType", alert.alertType)
            put("actual", alert.actual)
            put("planned", alert.planned)
            put("title", alert.title)
            put("message", alert.message)
        }.toString()
        val request = Request.Builder()
            .url("$baseUrl/api/v1/alerts/threshold")
            .header("Authorization", "Bearer $token")
            .post(payload.toRequestBody(JSON))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) error("Email alert failed (${response.code})")
            return true
        }
    }

    private companion object {
        val JSON = "application/json; charset=utf-8".toMediaType()
    }
}
