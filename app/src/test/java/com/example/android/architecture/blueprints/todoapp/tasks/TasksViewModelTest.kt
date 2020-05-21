package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalStdlibApi
class TasksViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var tasksRepository: FakeTestRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel() {
        tasksRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2= Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        tasksViewModel = tasksViewModel(tasksRepository)
    }

    @Test
    fun addNewTask_setNewTaskEvent(){

        //WHEN adding a new task
        tasksViewModel.addNewTask()

        //THEN the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        //WHEN
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        //THEN
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is` {true})

    }
    @Test
    fun completeTask_dataAndSnackbarUpdated() {

        //With a repository that has an active task
        val task = Task("Title", "Description")
        tasksRepository.addTasks(task)


        tasksViewModel.completeTask(task, true)
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is` {true})

        val snackbarText: Event<Int> = tasksViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled(), `is` (R.string.task_marked_complete))

    }
}