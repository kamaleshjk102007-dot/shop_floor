package com.example.dashboard

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class DashboardPersistenceTest {

    private val context: Context
        get() = ApplicationProvider.getApplicationContext()

    @Before
    @After
    fun clearStorage() {
        context.getSharedPreferences("shop_floor_operational_data", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .commit()
    }

    @Test
    fun operationalDataSurvivesViewModelRecreation() {
        val firstInstance = DashboardViewModel(context)
        firstInstance.addNewDepartment("D01", "Fabrication", "Fabrication station")
        firstInstance.addNewCategory("C01", "Welder", 325.0, listOf("Welding"), "Fabrication")
        firstInstance.addNewTask(
            id = "SO-1001",
            item = "Customer A",
            targetQty = 25,
            department = "Fabrication",
            plannedManhours = 12.0,
            plannedBudget = 5000.0
        )
        firstInstance.addNewEmployee(
            empId = "EMP001",
            name = "Test Employee",
            department = "Fabrication",
            category = "Welder",
            task = "SO-1001",
            status = "Active",
            hourlyRate = 325.0,
            skillLevel = "Expert"
        )
        firstInstance.updateEmployeeHours("EMP001", "Test Employee", 4.5)
        firstInstance.updateSalesOrderTimer("SO-1001", 7200L, "Paused")

        val restoredInstance = DashboardViewModel(context)

        assertEquals(1, restoredInstance.departments.value.size)
        assertEquals(1, restoredInstance.categories.value.size)
        assertEquals(1, restoredInstance.salesOrders.value.size)
        assertEquals(1, restoredInstance.employees.value.size)
        assertEquals(4.5, restoredInstance.employees.value.single().hoursClocked, 0.0)
        assertEquals(325.0, restoredInstance.employees.value.single().hourlyRate, 0.0)
        assertEquals(7200L, restoredInstance.salesOrders.value.single().timerSeconds)
        assertEquals("Paused", restoredInstance.salesOrders.value.single().status)
    }
}
