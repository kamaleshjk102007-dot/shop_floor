package com.example.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class LocalOperationalStoreTest {
    @Test
    fun snapshotAndDeletedRecordAreStoredLocally() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()
        val store = LocalOperationalStore(context)
        val id = "SO-${UUID.randomUUID()}"
        val record = JSONObject().put("id", id).put("item", "Persistence test")
        val snapshot = JSONObject()
            .put("salesOrders", JSONArray().put(record))
            .put("employees", JSONArray())
            .put("departments", JSONArray())
            .put("categories", JSONArray())
            .put("assignments", JSONArray())
            .put("logs", JSONArray())

        store.persistSnapshot(snapshot.toString())
        store.archive("salesOrders", id, record.toString())
        store.setRetentionDays(7)

        assertTrue(File(store.backupPath()).readText().contains(id))
        val archived = store.getRecoverableRecords().first { it.recordId == id }
        assertTrue(archived.expiresAt - archived.deletedAt <= 7L * 24L * 60L * 60L * 1000L)
    }
}
