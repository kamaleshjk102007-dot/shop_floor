package com.example.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.MainActivity
import com.example.R
import com.example.dashboard.EmployeeActivity
import com.example.dashboard.SalesOrder
import java.util.Locale

data class ThresholdAlert(
    val deliveryId: String,
    val orderId: String,
    val alertType: String,
    val actual: Double,
    val planned: Double,
    val title: String,
    val message: String
)

class ThresholdNotificationManager(context: Context) {
    private val appContext = context.applicationContext
    private val state = appContext.getSharedPreferences("shopfloor_threshold_alerts", Context.MODE_PRIVATE)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = appContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "Planned limit alerts",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Alerts when actual labour hours or cost exceed the planned limit"
                }
            )
        }
    }

    fun evaluate(orders: List<SalesOrder>, employees: List<EmployeeActivity>): List<ThresholdAlert> {
        val pendingEmailAlerts = mutableListOf<ThresholdAlert>()
        orders.forEach { order ->
            val assigned = employees.filter { it.task.equals(order.id, ignoreCase = true) }
            val employeeHours = assigned.sumOf { it.hoursClocked.coerceAtLeast(0.0) }
            val actualHours = if (employeeHours > 0.0) employeeHours else order.timerSeconds.coerceAtLeast(0L) / 3600.0
            val actualCost = assigned.sumOf {
                it.hoursClocked.coerceAtLeast(0.0) * it.hourlyRate.coerceAtLeast(0.0)
            }.takeIf { it > 0.0 } ?: run {
                val averageRate = assigned.map { it.hourlyRate.coerceAtLeast(0.0) }
                    .takeIf { it.isNotEmpty() }?.average() ?: 0.0
                actualHours * averageRate
            }
            updateAlert(
                key = "hours:${order.id}",
                orderId = order.id,
                alertType = "hours",
                actual = actualHours,
                planned = order.plannedManhours,
                exceeded = order.plannedManhours > 0.0 && actualHours > order.plannedManhours,
                title = "${order.id} alert: Actual hours exceeded",
                message = "Actual hours exceeded the planned hours."
            )?.let(pendingEmailAlerts::add)
            updateAlert(
                key = "cost:${order.id}",
                orderId = order.id,
                alertType = "cost",
                actual = actualCost,
                planned = order.plannedBudget,
                exceeded = order.plannedBudget > 0.0 && actualCost > order.plannedBudget,
                title = "${order.id} alert: Actual cost exceeded",
                message = "Actual cost exceeded the planned cost."
            )?.let(pendingEmailAlerts::add)
        }
        return pendingEmailAlerts
    }

    fun markEmailSent(deliveryId: String) {
        state.edit().putBoolean("email:$deliveryId", true).apply()
    }

    private fun updateAlert(
        key: String,
        orderId: String,
        alertType: String,
        actual: Double,
        planned: Double,
        exceeded: Boolean,
        title: String,
        message: String
    ): ThresholdAlert? {
        val activeKey = "active:$key"
        val deliveryKey = "delivery:$key"
        val alreadyActive = state.getBoolean(activeKey, false)
        if (!exceeded) {
            if (alreadyActive) {
                state.edit().remove(activeKey).remove(deliveryKey).apply()
            }
            return null
        }

        val deliveryId = state.getString(deliveryKey, null)
            ?: "$key:${System.currentTimeMillis()}".also { generated ->
                state.edit().putString(deliveryKey, generated).apply()
            }
        if (!alreadyActive) {
            send(key, title, message)
            state.edit().putBoolean(activeKey, true).apply()
        }
        if (state.getBoolean("email:$deliveryId", false)) return null
        return ThresholdAlert(deliveryId, orderId, alertType, actual, planned, title, message)
    }

    private fun send(key: String, title: String, message: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(appContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return false
        val intent = Intent(appContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            appContext,
            key.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        NotificationManagerCompat.from(appContext).notify(key.hashCode(), notification)
        return true
    }

    private fun format(value: Double): String = String.format(Locale.US, "%.2f", value)

    private companion object {
        const val CHANNEL_ID = "planned_limit_alerts"
    }
}
