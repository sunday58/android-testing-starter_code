package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred() {
        //GIVEN
        val tasks = listOf<Task>(
                Task("title","description", isCompleted = false )
        )
        //WHEN
        val result = getActiveAndCompletedStats(tasks)

        //THEN
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat( result.activeTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        val tasks = listOf<Task>(
                Task("title","description", isCompleted = true ),
                Task("title","description", isCompleted = true ),
                Task("title","description", isCompleted = false ),
                Task("title","description", isCompleted = false ),
                        Task("title","description", isCompleted = false )





        )
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        val tasks = emptyList<Task>(
        )
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {
        val tasks = null
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
}