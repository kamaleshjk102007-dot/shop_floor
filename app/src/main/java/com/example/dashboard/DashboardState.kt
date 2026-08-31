package com.example.dashboard

import android.content.Context
import com.example.data.CloudSyncClient
import com.example.data.DeletedRecordEntity
import com.example.data.LocalOperationalStore
import com.example.notifications.ThresholdNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SalesOrder(
    val id: String,
    val item: String,
    val targetQty: Int,
    var completedQty: Int,
    val department: String,
    var status: String,
    val description: String = "",
    val plannedManhours: Double = 0.0,
    val plannedBudget: Double = 0.0,
    val startDate: String = "",
    val endDate: String = "",
    var timerSeconds: Long = 0L
)

data class EmployeeActivity(
    val name: String,
    var task: String,
    val department: String,
    var hoursClocked: Double,
    var status: String, // "Active", "Break", "Logged Out"
    val empId: String = "",
    val category: String = "Operator",
    val hourlyRate: Double = 45.0,
    val skillLevel: String = "Intermediate"
)

data class Department(
    val code: String,
    val name: String,
    val description: String
)

data class LabourCategory(
    val code: String,
    val name: String,
    val hourlyRate: Double,
    val workflows: List<String>,
    val department: String
)

data class LabourAssignment(
    val employeeId: String,
    val salesOrderId: String,
    val employeeName: String,
    val department: String,
    val category: String,
    val plannedHours: Double,
    val startDate: String = "",
    val endDate: String = "",
    val description: String = "",
    val status: String = "Assigned"
)

data class ActivityLog(
    val timestamp: String,
    val message: String,
    val type: String // "SUCCESS", "INFO", "WARNING"
)

class DashboardViewModel(context: Context) {

    private val appContext = context.applicationContext
    private val localStore = LocalOperationalStore(appContext)
    private val cloudSync = CloudSyncClient()
    private val thresholdNotifications = ThresholdNotificationManager(appContext)
    private val persistenceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val persistenceMutex = Mutex()

    private val preferences = context.applicationContext.getSharedPreferences(
        "shop_floor_operational_data",
        Context.MODE_PRIVATE
    )

    // State flows
    private val _salesOrders = MutableStateFlow<List<SalesOrder>>(emptyList())
    val salesOrders: StateFlow<List<SalesOrder>> = _salesOrders.asStateFlow()

    private val _employees = MutableStateFlow<List<EmployeeActivity>>(emptyList())
    val employees: StateFlow<List<EmployeeActivity>> = _employees.asStateFlow()

    private val _departments = MutableStateFlow<List<Department>>(emptyList())
    val departments: StateFlow<List<Department>> = _departments.asStateFlow()

    private val _categories = MutableStateFlow<List<LabourCategory>>(emptyList())
    val categories: StateFlow<List<LabourCategory>> = _categories.asStateFlow()

    private val _logs = MutableStateFlow<List<ActivityLog>>(emptyList())
    val logs: StateFlow<List<ActivityLog>> = _logs.asStateFlow()

    private val _assignments = MutableStateFlow<List<LabourAssignment>>(emptyList())
    val assignments: StateFlow<List<LabourAssignment>> = _assignments.asStateFlow()

    private val _voiceTimerSelections = MutableStateFlow<Map<String, String>>(emptyMap())
    val voiceTimerSelections: StateFlow<Map<String, String>> = _voiceTimerSelections.asStateFlow()

    private val _recoverableRecords = MutableStateFlow<List<DeletedRecordEntity>>(emptyList())
    val recoverableRecords: StateFlow<List<DeletedRecordEntity>> = _recoverableRecords.asStateFlow()

    private val _recycleRetentionDays = MutableStateFlow(localStore.retentionDays())
    val recycleRetentionDays: StateFlow<Int> = _recycleRetentionDays.asStateFlow()

    private val _backupLocationLabel = MutableStateFlow(localStore.backupLocationLabel())
    val backupLocationLabel: StateFlow<String> = _backupLocationLabel.asStateFlow()

    // Filters
    private val _selectedDepartment = MutableStateFlow("All")
    val selectedDepartment: StateFlow<String> = _selectedDepartment.asStateFlow()

    private val _selectedTimeframe = MutableStateFlow("Week") // "Day", "Week", "Month"
    val selectedTimeframe: StateFlow<String> = _selectedTimeframe.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        restoreData()
        removeDemoDataIfPresent()
        persistData()
        refreshRecycleBin()
    }

    private fun removeDemoDataIfPresent() {
        val demoOrderIds = setOf("SO-ALERT-01")
        val demoEmployeeIds = setOf("EMP-ALERT-01", "EMP-02", "EMP-03", "EMP-04")
        _salesOrders.value = _salesOrders.value.filterNot { order ->
            order.id.uppercase(Locale.ROOT) in demoOrderIds ||
                order.description.equals("Dashboard demo", true) ||
                order.item.startsWith("Demo ", true)
        }
        _employees.value = _employees.value.filterNot { employee ->
            employee.empId.uppercase(Locale.ROOT) in demoEmployeeIds ||
                employee.name.startsWith("Demo ", true) ||
                employee.name.equals("Alert Test Employee", true)
        }
        _assignments.value = _assignments.value.filterNot { assignment ->
            assignment.salesOrderId.uppercase(Locale.ROOT) in demoOrderIds ||
                assignment.employeeId.uppercase(Locale.ROOT) in demoEmployeeIds
        }
        _logs.value = _logs.value.filterNot { log -> log.message.startsWith("Demo charts loaded", true) }
    }

    private fun restoreData() {
        val savedData = preferences.getString("dashboard_state", null) ?: return
        runCatching {
            val root = JSONObject(savedData)
            _salesOrders.value = root.optJSONArray("salesOrders").toSalesOrders()
            _employees.value = root.optJSONArray("employees").toEmployees()
            _departments.value = root.optJSONArray("departments").toDepartments()
            _categories.value = root.optJSONArray("categories").toCategories()
            _logs.value = root.optJSONArray("logs").toActivityLogs()
            _assignments.value = root.optJSONArray("assignments").toAssignments()
            val repairedOrders = _salesOrders.value.map { order ->
                if (order.department.isBlank() || order.department.equals("Unassigned", true)) {
                    val assignedDepartment = _employees.value.firstOrNull { employee ->
                        employee.task.equals(order.id, true) &&
                            employee.department.isNotBlank() &&
                            !employee.department.equals("Unassigned", true)
                    }?.department
                    if (assignedDepartment != null) order.copy(department = assignedDepartment) else order
                } else order
            }
            if (repairedOrders != _salesOrders.value) {
                _salesOrders.value = repairedOrders
                persistData()
            }
        }
    }

    private fun buildSnapshot(): JSONObject = JSONObject().apply {
            put("revision", System.currentTimeMillis())
            put("recycleRetentionDays", _recycleRetentionDays.value)
            put("salesOrders", JSONArray().apply {
                _salesOrders.value.forEach { order ->
                    put(JSONObject().apply {
                        put("id", order.id)
                        put("item", order.item)
                        put("targetQty", order.targetQty)
                        put("completedQty", order.completedQty)
                        put("department", order.department)
                        put("status", order.status)
                        put("description", order.description)
                        put("plannedManhours", order.plannedManhours)
                        put("plannedBudget", order.plannedBudget)
                        put("startDate", order.startDate)
                        put("endDate", order.endDate)
                        put("timerSeconds", order.timerSeconds)
                    })
                }
            })
            put("employees", JSONArray().apply {
                _employees.value.forEach { employee ->
                    put(JSONObject().apply {
                        put("name", employee.name)
                        put("task", employee.task)
                        put("department", employee.department)
                        put("hoursClocked", employee.hoursClocked)
                        put("status", employee.status)
                        put("empId", employee.empId)
                        put("category", employee.category)
                        put("hourlyRate", employee.hourlyRate)
                        put("skillLevel", employee.skillLevel)
                    })
                }
            })
            put("departments", JSONArray().apply {
                _departments.value.forEach { department ->
                    put(JSONObject().apply {
                        put("code", department.code)
                        put("name", department.name)
                        put("description", department.description)
                    })
                }
            })
            put("categories", JSONArray().apply {
                _categories.value.forEach { category ->
                    put(JSONObject().apply {
                        put("code", category.code)
                        put("name", category.name)
                        put("hourlyRate", category.hourlyRate)
                        put("workflows", JSONArray(category.workflows))
                        put("department", category.department)
                    })
                }
            })
            put("logs", JSONArray().apply {
                _logs.value.forEach { log ->
                    put(JSONObject().apply {
                        put("timestamp", log.timestamp)
                        put("message", log.message)
                        put("type", log.type)
                    })
                }
            })
            put("assignments", JSONArray().apply {
                _assignments.value.forEach { assignment ->
                    put(JSONObject().apply {
                        put("employeeId", assignment.employeeId)
                        put("salesOrderId", assignment.salesOrderId)
                        put("employeeName", assignment.employeeName)
                        put("department", assignment.department)
                        put("category", assignment.category)
                        put("plannedHours", assignment.plannedHours)
                        put("startDate", assignment.startDate)
                        put("endDate", assignment.endDate)
                        put("description", assignment.description)
                        put("status", assignment.status)
                    })
                }
            })
        }

    private fun persistData() {
        val snapshot = buildSnapshot().toString()
        preferences.edit().putString("dashboard_state", snapshot).apply()
        val pendingAlerts = thresholdNotifications.evaluate(_salesOrders.value, _employees.value)
        persistenceScope.launch {
            persistenceMutex.withLock {
                runCatching { localStore.persistSnapshot(snapshot) }
                runCatching { cloudSync.pushSnapshot(snapshot) }
                pendingAlerts.forEach { alert ->
                    if (runCatching { cloudSync.sendThresholdAlert(alert) }.getOrDefault(false)) {
                        thresholdNotifications.markEmailSent(alert.deliveryId)
                    }
                }
            }
        }
    }

    private fun archiveRecord(recordType: String, recordId: String, payload: JSONObject) {
        persistenceScope.launch {
            persistenceMutex.withLock {
                runCatching { localStore.archive(recordType, recordId, payload.toString()) }
                _recoverableRecords.value = runCatching { localStore.getRecoverableRecords() }
                    .getOrDefault(_recoverableRecords.value)
            }
        }
    }

    private fun archiveCurrentRecord(recordType: String, idField: String, recordId: String) {
        val records = buildSnapshot().optJSONArray(recordType) ?: return
        for (index in 0 until records.length()) {
            val payload = records.optJSONObject(index) ?: continue
            if (payload.optString(idField).equals(recordId, ignoreCase = true)) {
                archiveRecord(recordType, recordId, payload)
                return
            }
        }
    }

    fun localBackupLocation(): String = localStore.backupPath()

    fun setBackupLocation(treeUri: String, label: String) {
        persistenceScope.launch {
            persistenceMutex.withLock {
                runCatching { localStore.setBackupTreeUri(treeUri, label) }
                    .onSuccess { _backupLocationLabel.value = localStore.backupLocationLabel() }
            }
            persistData()
        }
    }

    fun refreshRecycleBin() {
        persistenceScope.launch {
            persistenceMutex.withLock {
                _recoverableRecords.value = runCatching { localStore.getRecoverableRecords() }
                    .getOrDefault(emptyList())
            }
        }
    }

    fun setRecycleRetentionDays(days: Int) {
        val safeDays = days.coerceIn(1, 365)
        _recycleRetentionDays.value = safeDays
        persistenceScope.launch {
            persistenceMutex.withLock {
                runCatching { localStore.setRetentionDays(safeDays) }
                _recoverableRecords.value = runCatching { localStore.getRecoverableRecords() }
                    .getOrDefault(emptyList())
            }
            persistData()
        }
    }

    fun restoreDeletedRecord(archiveId: String) {
        val archived = _recoverableRecords.value.firstOrNull { it.archiveId == archiveId } ?: return
        val payload = runCatching { JSONObject(archived.payload) }.getOrNull() ?: return
        when (archived.recordType) {
            "salesOrders" -> JSONArray().put(payload).toSalesOrders().firstOrNull()?.let { restored ->
                _salesOrders.value = listOf(restored) + _salesOrders.value.filterNot { it.id.equals(restored.id, true) }
                val assignedIds = payload.optJSONArray("assignedEmployeeIds")
                if (assignedIds != null) {
                    val ids = buildSet {
                        for (index in 0 until assignedIds.length()) add(assignedIds.optString(index))
                    }
                    _employees.value = _employees.value.map { employee ->
                        if (employee.empId in ids) employee.copy(task = restored.id) else employee
                    }
                }
            }
            "employees" -> JSONArray().put(payload).toEmployees().firstOrNull()?.let { restored ->
                _employees.value = listOf(restored) + _employees.value.filterNot { it.empId.equals(restored.empId, true) }
            }
            "departments" -> JSONArray().put(payload).toDepartments().firstOrNull()?.let { restored ->
                _departments.value = listOf(restored) + _departments.value.filterNot { it.code.equals(restored.code, true) }
            }
            "categories" -> JSONArray().put(payload).toCategories().firstOrNull()?.let { restored ->
                _categories.value = listOf(restored) + _categories.value.filterNot { it.code.equals(restored.code, true) }
            }
            "assignments" -> JSONArray().put(payload).toAssignments().firstOrNull()?.let { restored ->
                _assignments.value = listOf(restored) + _assignments.value.filterNot {
                    it.employeeId.equals(restored.employeeId, true) && it.salesOrderId.equals(restored.salesOrderId, true)
                }
            }
            "logs" -> JSONArray().put(payload).toActivityLogs().firstOrNull()?.let { restored ->
                _logs.value = listOf(restored) + _logs.value
            }
            else -> return
        }
        persistenceScope.launch {
            persistenceMutex.withLock {
                runCatching { localStore.removeArchive(archiveId) }
                _recoverableRecords.value = runCatching { localStore.getRecoverableRecords() }
                    .getOrDefault(emptyList())
            }
        }
        addLog("Restored ${archived.recordType} record ${archived.recordId} from Recycle Bin", "SUCCESS")
    }

    private fun archiveAllCurrentRecords() {
        val root = buildSnapshot()
        val identifiers = mapOf(
            "salesOrders" to "id",
            "employees" to "empId",
            "departments" to "code",
            "categories" to "code",
            "assignments" to "employeeId",
            "logs" to "timestamp"
        )
        identifiers.forEach { (type, idField) ->
            val records = root.optJSONArray(type) ?: return@forEach
            for (index in 0 until records.length()) {
                val payload = records.optJSONObject(index) ?: continue
                val baseId = payload.optString(idField).ifBlank { index.toString() }
                val recordId = if (type == "assignments") {
                    "$baseId:${payload.optString("salesOrderId")}"
                } else baseId
                archiveRecord(type, recordId, payload)
            }
        }
    }

    fun setDepartment(dept: String) {
        _selectedDepartment.value = dept
    }

    fun setTimeframe(time: String) {
        _selectedTimeframe.value = time
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSalesOrderTimer(soNumber: String, timerSeconds: Long, status: String) {
        val ordersList = _salesOrders.value.toMutableList()
        val index = ordersList.indexOfFirst { it.id == soNumber }
        if (index >= 0) {
            val order = ordersList[index]
            ordersList[index] = order.copy(timerSeconds = timerSeconds, status = status)
            _salesOrders.value = ordersList
            persistData()
        }
    }

    fun startVoiceTimer(employeeId: String, orderId: String): Boolean {
        val order = _salesOrders.value.firstOrNull { it.id.equals(orderId, ignoreCase = true) } ?: return false
        val employees = _employees.value.toMutableList()
        val employeeIndex = employees.indexOfFirst {
            it.empId.equals(employeeId, ignoreCase = true) || it.name.equals(employeeId, ignoreCase = true)
        }
        if (employeeIndex < 0) return false
        val employee = employees[employeeIndex]
        employees[employeeIndex] = employee.copy(task = order.id, status = "Active")
        _employees.value = employees
        _voiceTimerSelections.value = _voiceTimerSelections.value + (order.id to employee.empId)
        updateSalesOrderTimer(order.id, order.timerSeconds, "Running")
        addLog("Voice command started ${employee.name}'s timer on ${order.id}", "SUCCESS")
        persistData()
        return true
    }

    fun stopVoiceTimer(orderId: String): Boolean {
        val order = _salesOrders.value.firstOrNull { it.id.equals(orderId, ignoreCase = true) } ?: return false
        _voiceTimerSelections.value = _voiceTimerSelections.value - order.id
        updateSalesOrderTimer(order.id, 0L, "Paused")
        addLog("Voice command paused and reset timer for ${order.id}", "INFO")
        return true
    }

    fun saveAssignment(assignment: LabourAssignment): Boolean {
        val employeeIndex = _employees.value.indexOfFirst { it.empId.equals(assignment.employeeId, true) }
        if (employeeIndex < 0 || _salesOrders.value.none { it.id.equals(assignment.salesOrderId, true) }) return false
        val employees = _employees.value.toMutableList()
        employees[employeeIndex] = employees[employeeIndex].copy(task = assignment.salesOrderId, status = "Active")
        _employees.value = employees
        // Keep department-based charts consistent when an unassigned order receives labour.
        _salesOrders.value = _salesOrders.value.map { order ->
            if (order.id.equals(assignment.salesOrderId, true) &&
                (order.department.isBlank() || order.department.equals("Unassigned", true))) {
                order.copy(department = assignment.department)
            } else order
        }
        val list = _assignments.value.toMutableList()
        val existing = list.indexOfFirst {
            it.employeeId.equals(assignment.employeeId, true) && it.salesOrderId.equals(assignment.salesOrderId, true)
        }
        if (existing >= 0) list[existing] = assignment else list.add(0, assignment)
        _assignments.value = list
        addLog("Assigned ${assignment.employeeName} to ${assignment.salesOrderId}", "SUCCESS")
        return true
    }

    fun startVoiceWork(orderId: String, employeeIds: List<String>): Boolean {
        val order = _salesOrders.value.firstOrNull { it.id.equals(orderId, true) } ?: return false
        val requested = employeeIds.map { it.lowercase() }.toSet()
        val matches = _employees.value.filter { employee ->
            requested.isEmpty() && employee.task.equals(order.id, true) ||
                employee.empId.lowercase() in requested || employee.name.lowercase() in requested
        }
        if (matches.isEmpty()) return false
        val matchIds = matches.map { it.empId }.toSet()
        _employees.value = _employees.value.map { employee ->
            if (employee.empId in matchIds) employee.copy(task = order.id, status = "Active") else employee
        }
        _voiceTimerSelections.value = _voiceTimerSelections.value + (order.id to matches.joinToString("|") { it.empId })
        updateSalesOrderTimer(order.id, order.timerSeconds, "Running")
        addLog("Started work for ${matches.joinToString { it.name }} on ${order.id}", "SUCCESS")
        return true
    }

    fun updateEmployeeHours(empId: String, name: String, hoursClocked: Double) {
        val empList = _employees.value.toMutableList()
        val index = empList.indexOfFirst { (empId.isNotEmpty() && it.empId == empId) || it.name.equals(name, ignoreCase = true) }
        if (index >= 0) {
            val emp = empList[index]
            empList[index] = emp.copy(hoursClocked = hoursClocked)
            _employees.value = empList
            persistData()
        }
    }

    // Helper to get formatted current time
    private fun getCurrentTimeString(): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }

    // Interactive Actions
    fun clockInEmployee(name: String, department: String, task: String) {
        val currentList = _employees.value.toMutableList()
        val index = currentList.indexOfFirst { it.name.lowercase() == name.lowercase() }
        
        if (index >= 0) {
            val emp = currentList[index]
            emp.status = "Active"
            emp.task = task
            // Department could change if they relocate
            currentList[index] = emp.copy(status = "Active", task = task, department = department)
        } else {
            currentList.add(EmployeeActivity(name, task, department, 0.0, "Active"))
        }
        
        _employees.value = currentList
        addLog("$name clocked in at $department station: '$task'", "SUCCESS")
    }

    fun clockOutEmployee(name: String) {
        val currentList = _employees.value.toMutableList()
        val index = currentList.indexOfFirst { it.name.lowercase() == name.lowercase() }
        if (index >= 0) {
            val emp = currentList[index]
            currentList[index] = emp.copy(status = "Logged Out")
            _employees.value = currentList
            addLog("$name clocked out", "WARNING")
        }
    }

    fun logLabour(orderId: String, completedQtyDelta: Int, hoursDelta: Double, employeeName: String) {
        // Update sales order
        val ordersList = _salesOrders.value.toMutableList()
        val orderIndex = ordersList.indexOfFirst { it.id == orderId }
        var orderItem = "Item"
        
        if (orderIndex >= 0) {
            val order = ordersList[orderIndex]
            val newCompleted = (order.completedQty + completedQtyDelta).coerceAtMost(order.targetQty)
            val newStatus = if (newCompleted >= order.targetQty) "Completed" else "In Progress"
            
            ordersList[orderIndex] = order.copy(completedQty = newCompleted, status = newStatus)
            _salesOrders.value = ordersList
            orderItem = order.item
        }

        // Update employee clocked hours
        val empList = _employees.value.toMutableList()
        val empIndex = empList.indexOfFirst { it.name == employeeName }
        if (empIndex >= 0) {
            val emp = empList[empIndex]
            empList[empIndex] = emp.copy(hoursClocked = emp.hoursClocked + hoursDelta)
            _employees.value = empList
        }

        addLog("$employeeName logged $hoursDelta hrs on $orderId ($orderItem): +$completedQtyDelta units", "SUCCESS")
    }

    fun addNewTask(
        id: String,
        item: String,
        targetQty: Int,
        department: String,
        description: String = "",
        plannedManhours: Double = 0.0,
        plannedBudget: Double = 0.0,
        startDate: String = "",
        endDate: String = "",
        status: String = "Not Started"
    ) {
        val ordersList = _salesOrders.value.toMutableList()
        ordersList.add(0, SalesOrder(
            id = id.trim().uppercase(),
            item = item.trim(),
            targetQty = targetQty,
            completedQty = 0,
            department = department,
            status = status,
            description = description,
            plannedManhours = plannedManhours,
            plannedBudget = plannedBudget,
            startDate = startDate,
            endDate = endDate
        ))
        _salesOrders.value = ordersList
        addLog("New Sales Order $id created for $department department", "INFO")
    }

    fun addNewDepartment(code: String, name: String, description: String) {
        val list = _departments.value.toMutableList()
        list.add(Department(code.trim().uppercase(), name.trim(), description.trim()))
        _departments.value = list
        addLog("New Department '$name' ($code) registered", "SUCCESS")
    }

    fun addNewCategory(code: String, name: String, hourlyRate: Double, workflows: List<String>, department: String) {
        val list = _categories.value.toMutableList()
        list.add(LabourCategory(code.trim().uppercase(), name.trim(), hourlyRate, workflows, department))
        _categories.value = list
        addLog("New Labour Category '$name' ($code) added under $department", "SUCCESS")
    }

    fun addNewEmployee(empId: String, name: String, department: String, category: String, task: String, status: String, hourlyRate: Double, skillLevel: String) {
        val list = _employees.value.toMutableList()
        list.add(0, EmployeeActivity(
            name = name.trim(),
            task = task.trim().ifEmpty { "Assigned Duty" },
            department = department,
            hoursClocked = 0.0,
            status = status,
            empId = empId.trim().uppercase(),
            category = category,
            hourlyRate = hourlyRate,
            skillLevel = skillLevel
        ))
        _employees.value = list
        addLog("Employee $name ($empId) registered at $department department", "SUCCESS")
    }

    fun updateSalesOrder(updatedOrder: SalesOrder) {
        val list = _salesOrders.value.toMutableList()
        val index = list.indexOfFirst { it.id == updatedOrder.id }
        if (index >= 0) {
            list[index] = updatedOrder
            _salesOrders.value = list
            addLog("Sales Order ${updatedOrder.id} updated successfully", "INFO")
        }
    }

    fun deleteSalesOrder(orderId: String) {
        buildSnapshot().optJSONArray("salesOrders")?.let { records ->
            for (index in 0 until records.length()) {
                val payload = records.optJSONObject(index) ?: continue
                if (payload.optString("id").equals(orderId, true)) {
                    payload.put(
                        "assignedEmployeeIds",
                        JSONArray(_employees.value.filter { it.task.equals(orderId, true) }.map { it.empId })
                    )
                    archiveRecord("salesOrders", orderId, payload)
                    break
                }
            }
        }
        val list = _salesOrders.value.toMutableList()
        list.removeAll { it.id == orderId }
        _salesOrders.value = list

        val empList = _employees.value.toMutableList()
        var updatedCount = 0
        empList.forEachIndexed { idx, emp ->
            if (emp.task == orderId) {
                empList[idx] = emp.copy(task = "Unassigned")
                updatedCount++
            }
        }
        if (updatedCount > 0) {
            _employees.value = empList
        }
        addLog("Sales Order $orderId deleted ($updatedCount employees unassigned)", "WARNING")
    }

    fun clearAllData() {
        archiveAllCurrentRecords()
        _salesOrders.value = emptyList()
        _employees.value = emptyList()
        _departments.value = emptyList()
        _categories.value = emptyList()
        _logs.value = emptyList()
        addLog("All data cleared by user", "WARNING")
    }

    fun updateDepartment(updatedDept: Department, oldCode: String) {
        val list = _departments.value.toMutableList()
        val index = list.indexOfFirst { it.code == oldCode }
        if (index >= 0) {
            val oldDept = list[index]
            list[index] = updatedDept
            _departments.value = list

            // Cascade department updates to Sales Orders and Employees
            val updatedOrders = _salesOrders.value.map { order ->
                if (order.department.equals(oldDept.name, ignoreCase = true) || order.department.equals(oldCode, ignoreCase = true)) {
                    order.copy(department = updatedDept.name)
                } else order
            }
            _salesOrders.value = updatedOrders

            val updatedEmps = _employees.value.map { emp ->
                if (emp.department.equals(oldDept.name, ignoreCase = true) || emp.department.equals(oldCode, ignoreCase = true)) {
                    emp.copy(department = updatedDept.name)
                } else emp
            }
            _employees.value = updatedEmps

            addLog("Department ${updatedDept.name} updated across all modules", "INFO")
        }
    }

    fun deleteDepartment(deptCode: String) {
        archiveCurrentRecord("departments", "code", deptCode)
        val list = _departments.value.toMutableList()
        val dept = list.find { it.code == deptCode }
        if (dept != null) {
            list.remove(dept)
            _departments.value = list
            addLog("Department ${dept.name} ($deptCode) deleted", "WARNING")
        }
    }

    fun updateLabourCategory(updatedCat: LabourCategory, oldCode: String) {
        val list = _categories.value.toMutableList()
        val index = list.indexOfFirst { it.code == oldCode }
        if (index >= 0) {
            val oldCat = list[index]
            list[index] = updatedCat
            _categories.value = list

            // Cascade category updates to Employees
            val updatedEmps = _employees.value.map { emp ->
                if (emp.category.equals(oldCat.name, ignoreCase = true) || emp.category.equals(oldCode, ignoreCase = true)) {
                    emp.copy(category = updatedCat.name, hourlyRate = updatedCat.hourlyRate)
                } else emp
            }
            _employees.value = updatedEmps

            addLog("Labour Category ${updatedCat.name} updated across all employees", "INFO")
        }
    }

    fun deleteLabourCategory(catCode: String) {
        archiveCurrentRecord("categories", "code", catCode)
        val list = _categories.value.toMutableList()
        val cat = list.find { it.code == catCode }
        if (cat != null) {
            list.remove(cat)
            _categories.value = list
            addLog("Labour Category ${cat.name} deleted", "WARNING")
        }
    }

    fun updateEmployee(updatedEmp: EmployeeActivity) {
        val list = _employees.value.toMutableList()
        val index = list.indexOfFirst { it.empId == updatedEmp.empId }
        if (index >= 0) {
            list[index] = updatedEmp
            _employees.value = list
            addLog("Employee ${updatedEmp.name} (${updatedEmp.empId}) updated", "INFO")
        }
    }

    fun deleteEmployee(empId: String) {
        archiveCurrentRecord("employees", "empId", empId)
        val list = _employees.value.toMutableList()
        val emp = list.find { it.empId == empId }
        if (emp != null) {
            list.remove(emp)
            _employees.value = list
            addLog("Employee ${emp.name} deleted", "WARNING")
        }
    }

    fun moveEmployees(employeeIds: List<String>, destinationOrderId: String) {
        val list = _employees.value.toMutableList()
        var movedCount = 0
        list.forEachIndexed { idx, emp ->
            if (emp.empId in employeeIds) {
                list[idx] = emp.copy(task = destinationOrderId)
                movedCount++
            }
        }
        if (movedCount > 0) {
            _employees.value = list
            val destinationDepartment = list.firstOrNull { it.empId in employeeIds }?.department.orEmpty()
            if (destinationDepartment.isNotBlank() && !destinationOrderId.equals("Unassigned", true)) {
                _salesOrders.value = _salesOrders.value.map { order ->
                    if (order.id.equals(destinationOrderId, true) &&
                        (order.department.isBlank() || order.department.equals("Unassigned", true))) {
                        order.copy(department = destinationDepartment)
                    } else order
                }
            }
            addLog("Moved $movedCount employee(s) to Sales Order $destinationOrderId", "SUCCESS")
        }
    }

    fun addLog(message: String, type: String) {
        val currentLogs = _logs.value.toMutableList()
        currentLogs.add(0, ActivityLog(getCurrentTimeString(), message, type))
        if (currentLogs.size > 20) {
            currentLogs.removeAt(currentLogs.size - 1)
        }
        _logs.value = currentLogs
        persistData()
    }

    // Completion percentages calculated from the current order data.
    fun getProductivityStatsForChart(dept: String, timeframe: String): List<Float> {
        val departmentNames = listOf("Assembly", "Machining", "Quality", "Packing")
        return departmentNames.map { departmentName ->
            if (dept != "All" && dept != departmentName) {
                0f
            } else {
                val orders = _salesOrders.value.filter { it.department == departmentName }
                val target = orders.sumOf { it.targetQty }
                if (target == 0) 0f
                else (orders.sumOf { it.completedQty } * 100f / target).coerceIn(0f, 100f)
            }
        }
    }
}

private fun JSONArray?.toAssignments(): List<LabourAssignment> = buildList {
    val array = this@toAssignments ?: return@buildList
    for (index in 0 until array.length()) {
        val item = array.optJSONObject(index) ?: continue
        add(LabourAssignment(
            employeeId = item.optString("employeeId"), salesOrderId = item.optString("salesOrderId"),
            employeeName = item.optString("employeeName"), department = item.optString("department"),
            category = item.optString("category"), plannedHours = item.optDouble("plannedHours"),
            startDate = item.optString("startDate"), endDate = item.optString("endDate"),
            description = item.optString("description"), status = item.optString("status", "Assigned")
        ))
    }
}

private fun JSONArray?.toSalesOrders(): List<SalesOrder> = buildList {
    val array = this@toSalesOrders ?: return@buildList
    for (index in 0 until array.length()) {
        val item = array.optJSONObject(index) ?: continue
        add(
            SalesOrder(
                id = item.optString("id"),
                item = item.optString("item"),
                targetQty = item.optInt("targetQty"),
                completedQty = item.optInt("completedQty"),
                department = item.optString("department"),
                status = item.optString("status"),
                description = item.optString("description"),
                plannedManhours = item.optDouble("plannedManhours", 0.0),
                plannedBudget = item.optDouble("plannedBudget", 0.0),
                startDate = item.optString("startDate"),
                endDate = item.optString("endDate"),
                timerSeconds = item.optLong("timerSeconds")
            )
        )
    }
}

private fun JSONArray?.toEmployees(): List<EmployeeActivity> = buildList {
    val array = this@toEmployees ?: return@buildList
    for (index in 0 until array.length()) {
        val item = array.optJSONObject(index) ?: continue
        add(
            EmployeeActivity(
                name = item.optString("name"),
                task = item.optString("task"),
                department = item.optString("department"),
                hoursClocked = item.optDouble("hoursClocked", 0.0),
                status = item.optString("status"),
                empId = item.optString("empId"),
                category = item.optString("category"),
                hourlyRate = item.optDouble("hourlyRate", 0.0),
                skillLevel = item.optString("skillLevel")
            )
        )
    }
}

private fun JSONArray?.toDepartments(): List<Department> = buildList {
    val array = this@toDepartments ?: return@buildList
    for (index in 0 until array.length()) {
        val item = array.optJSONObject(index) ?: continue
        add(
            Department(
                code = item.optString("code"),
                name = item.optString("name"),
                description = item.optString("description")
            )
        )
    }
}

private fun JSONArray?.toCategories(): List<LabourCategory> = buildList {
    val array = this@toCategories ?: return@buildList
    for (index in 0 until array.length()) {
        val item = array.optJSONObject(index) ?: continue
        val workflowsJson = item.optJSONArray("workflows")
        val workflows = buildList {
            if (workflowsJson != null) {
                for (workflowIndex in 0 until workflowsJson.length()) {
                    add(workflowsJson.optString(workflowIndex))
                }
            }
        }
        add(
            LabourCategory(
                code = item.optString("code"),
                name = item.optString("name"),
                hourlyRate = item.optDouble("hourlyRate", 0.0),
                workflows = workflows,
                department = item.optString("department")
            )
        )
    }
}

private fun JSONArray?.toActivityLogs(): List<ActivityLog> = buildList {
    val array = this@toActivityLogs ?: return@buildList
    for (index in 0 until array.length()) {
        val item = array.optJSONObject(index) ?: continue
        add(
            ActivityLog(
                timestamp = item.optString("timestamp"),
                message = item.optString("message"),
                type = item.optString("type")
            )
        )
    }
}
