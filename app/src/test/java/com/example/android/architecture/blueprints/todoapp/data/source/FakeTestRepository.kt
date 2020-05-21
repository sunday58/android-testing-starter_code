package com.example.android.architecture.blueprints.todoapp.data.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.TodoApplication
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.runBlocking

class FakeTestRepository() : TasksRepository {

    private var shouldReturnError = false

    var tasksServiceData: LinkedHashMap<String, Task> = LinkedHashMap()
    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        if (shouldReturnError) {
            return Result.Error(java.lang.Exception("Test exception"))
        }
        return Result.Success(tasksServiceData.values.toList())
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        if (shouldReturnError) {
            return Result.Error(java.lang.Exception("Test exception"))
        }
       tasksServiceData[taskId]?.let {
           return Result.Success(it)
       }
        return Result.Error(Exception("Could not find task"))
    }

    override suspend fun saveTask(task: Task) {
       tasksServiceData[task.id] = task
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    fun addTasks(vararg tasks: Task) {
        for (task in tasks) {
            tasksServiceData[task.id] = task
        }
        runBlocking { refreshTasks() }
    }
}