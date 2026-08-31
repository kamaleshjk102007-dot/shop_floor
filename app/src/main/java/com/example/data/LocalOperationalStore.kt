package com.example.data

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import org.json.JSONObject
import java.io.File

@Entity(tableName = "operational_records")
data class OperationalRecordEntity(
    @PrimaryKey val key: String,
    val recordType: String,
    val recordId: String,
    val payload: String,
    val updatedAt: Long
)

@Entity(tableName = "deleted_records")
data class DeletedRecordEntity(
    @PrimaryKey val archiveId: String,
    val recordType: String,
    val recordId: String,
    val payload: String,
    val deletedAt: Long,
    val expiresAt: Long
)

@Dao
interface OperationalRecordDao {
    @Query("DELETE FROM operational_records")
    suspend fun deleteActiveRecords()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertActiveRecords(records: List<OperationalRecordEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun archive(record: DeletedRecordEntity)

    @Query("DELETE FROM deleted_records WHERE expiresAt <= :now")
    suspend fun purgeExpired(now: Long)

    @Query("SELECT * FROM deleted_records WHERE expiresAt > :now ORDER BY deletedAt DESC")
    suspend fun activeArchives(now: Long): List<DeletedRecordEntity>

    @Query("DELETE FROM deleted_records WHERE archiveId = :archiveId")
    suspend fun deleteArchive(archiveId: String)

    @Query("UPDATE deleted_records SET expiresAt = deletedAt + :retentionMillis")
    suspend fun updateRetention(retentionMillis: Long)

    @Transaction
    suspend fun replaceActiveRecords(records: List<OperationalRecordEntity>) {
        deleteActiveRecords()
        if (records.isNotEmpty()) upsertActiveRecords(records)
    }
}

@Database(
    entities = [OperationalRecordEntity::class, DeletedRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShopFloorDatabase : RoomDatabase() {
    abstract fun records(): OperationalRecordDao

    companion object {
        @Volatile private var instance: ShopFloorDatabase? = null

        fun get(context: Context): ShopFloorDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                ShopFloorDatabase::class.java,
                "shopfloor-local.db"
            ).build().also { instance = it }
        }
    }
}

class LocalOperationalStore(context: Context) {
    private val appContext = context.applicationContext
    private val dao = ShopFloorDatabase.get(appContext).records()
    private val settings = appContext.getSharedPreferences("shopfloor_recycle_settings", Context.MODE_PRIVATE)
    private val backupDirectory = File(appContext.filesDir, "operational-data")
    private val snapshotFile = File(backupDirectory, "shopfloor-data.json")

    suspend fun persistSnapshot(snapshot: String) {
        val now = System.currentTimeMillis()
        val root = JSONObject(snapshot)
        val records = buildList {
            addRecords(root, "salesOrders", "id", now)
            addRecords(root, "employees", "empId", now)
            addRecords(root, "departments", "code", now)
            addRecords(root, "categories", "code", now)
            addRecords(root, "assignments", null, now)
            addRecords(root, "logs", null, now)
        }
        dao.replaceActiveRecords(records)
        dao.purgeExpired(now)
        writeSnapshotAtomically(snapshot)
        writeSelectedFolderMirror(snapshot)
    }

    suspend fun archive(recordType: String, recordId: String, payload: String) {
        val deletedAt = System.currentTimeMillis()
        dao.archive(
            DeletedRecordEntity(
                archiveId = "$recordType:$recordId:$deletedAt",
                recordType = recordType,
                recordId = recordId,
                payload = payload,
                deletedAt = deletedAt,
                expiresAt = deletedAt + retentionDays() * ONE_DAY_MS
            )
        )
        dao.purgeExpired(deletedAt)
    }

    suspend fun getRecoverableRecords(): List<DeletedRecordEntity> =
        dao.activeArchives(System.currentTimeMillis())

    suspend fun removeArchive(archiveId: String) {
        dao.deleteArchive(archiveId)
    }

    suspend fun setRetentionDays(days: Int) {
        val safeDays = days.coerceIn(MIN_RETENTION_DAYS, MAX_RETENTION_DAYS)
        settings.edit().putInt("retention_days", safeDays).apply()
        dao.updateRetention(safeDays * ONE_DAY_MS)
        dao.purgeExpired(System.currentTimeMillis())
    }

    fun retentionDays(): Int = settings.getInt("retention_days", DEFAULT_RETENTION_DAYS)
        .coerceIn(MIN_RETENTION_DAYS, MAX_RETENTION_DAYS)

    fun backupPath(): String = snapshotFile.absolutePath

    fun backupLocationLabel(): String = settings.getString("backup_location_label", null)
        ?.takeIf { it.isNotBlank() }
        ?: "Protected app storage"

    fun backupTreeUri(): String = settings.getString("backup_tree_uri", "").orEmpty()

    fun setBackupTreeUri(uri: String, label: String) {
        settings.edit()
            .putString("backup_tree_uri", uri)
            .putString("backup_location_label", label.ifBlank { "Selected folder" })
            .apply()
        snapshotFile.takeIf(File::exists)?.readText(Charsets.UTF_8)?.let(::writeSelectedFolderMirror)
    }

    private fun MutableList<OperationalRecordEntity>.addRecords(
        root: JSONObject,
        type: String,
        idField: String?,
        now: Long
    ) {
        val array = root.optJSONArray(type) ?: return
        for (index in 0 until array.length()) {
            val item = array.optJSONObject(index) ?: continue
            val fallback = item.toString().hashCode().toUInt().toString(16)
            val recordId = idField?.let(item::optString).orEmpty().ifBlank { "$index-$fallback" }
            add(OperationalRecordEntity("$type:$recordId", type, recordId, item.toString(), now))
        }
    }

    private fun writeSnapshotAtomically(snapshot: String) {
        backupDirectory.mkdirs()
        val temporary = File(backupDirectory, "shopfloor-data.tmp")
        temporary.writeText(snapshot, Charsets.UTF_8)
        if (snapshotFile.exists()) snapshotFile.delete()
        check(temporary.renameTo(snapshotFile)) { "Unable to update local operational snapshot" }
    }

    private fun writeSelectedFolderMirror(snapshot: String) {
        val treeUri = backupTreeUri().takeIf { it.isNotBlank() }?.let(Uri::parse) ?: return
        runCatching {
            val resolver = appContext.contentResolver
            val treeDocumentId = DocumentsContract.getTreeDocumentId(treeUri)
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, treeDocumentId)
            var documentUri: Uri? = null
            resolver.query(
                childrenUri,
                arrayOf(DocumentsContract.Document.COLUMN_DOCUMENT_ID, DocumentsContract.Document.COLUMN_DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                val idIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
                val nameIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    if (cursor.getString(nameIndex) == MIRROR_FILE_NAME) {
                        documentUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, cursor.getString(idIndex))
                        break
                    }
                }
            }
            if (documentUri == null) {
                val parent = DocumentsContract.buildDocumentUriUsingTree(treeUri, treeDocumentId)
                documentUri = DocumentsContract.createDocument(resolver, parent, "application/json", MIRROR_FILE_NAME)
            }
            checkNotNull(documentUri) { "Unable to create backup mirror" }
            resolver.openOutputStream(documentUri!!, "wt")?.bufferedWriter(Charsets.UTF_8)?.use { writer ->
                writer.write(snapshot)
            } ?: error("Unable to open backup mirror")
        }
    }

    private companion object {
        const val ONE_DAY_MS = 24L * 60L * 60L * 1000L
        const val DEFAULT_RETENTION_DAYS = 30
        const val MIN_RETENTION_DAYS = 1
        const val MAX_RETENTION_DAYS = 365
        const val MIRROR_FILE_NAME = "shopfloor-data.json"
    }
}
