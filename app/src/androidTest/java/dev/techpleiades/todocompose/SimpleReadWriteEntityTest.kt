package dev.techpleiades.todocompose

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import dev.techpleiades.todocompose.datasource.daos.TaskDao
import dev.techpleiades.todocompose.datasource.database.TodoRoomDatabase
import dev.techpleiades.todocompose.datasource.models.TaskModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var taskDao: TaskDao
    private lateinit var db: TodoRoomDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            TodoRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        taskDao = db.TaskDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertTaskAndReadInList() = runBlocking {
        // arrange
        val fakeTaskName = "Task 1"
        val fakeTaskDescription = "This is the task description for test."
        val fakeTaskModel = TaskModel(id = 0, name = fakeTaskName, description = fakeTaskDescription)

        // act
        taskDao.insertByTask(fakeTaskModel)
        val taskList= taskDao.getAllAsFlow().first()

        // assert
        assertThat(taskList.count()).isEqualTo(1)
        assertThat(taskList.first().name).isEqualTo(fakeTaskName)
        assertThat(taskList.first().description).isEqualTo(fakeTaskDescription)
    }

    @Test
    fun testUpdateTaskAndReadInList() = runBlocking {
        // arrange
        val fakeTaskName = "Task 1"
        val fakeTaskDescription = "This is the task description for test."
        val fakeTaskNameUpdated = "Task 1 - Updated"
        val fakeTaskModel = TaskModel(id = 0, name = fakeTaskName, description = fakeTaskDescription)

        // act
        taskDao.insertByTask(fakeTaskModel)
        val taskList= taskDao.getAllAsFlow().first()
        taskDao.updateByTask(taskList.first().copy(name = fakeTaskNameUpdated))
        val taskListUpdated = taskDao.getAllAsFlow().first()

        // assert
        assertThat(taskListUpdated.count()).isEqualTo(1)
        assertThat(taskListUpdated.first().name).isEqualTo(fakeTaskNameUpdated)
        assertThat(taskListUpdated.first().description).isEqualTo(fakeTaskDescription)
    }

    @Test
    fun testDeleteTaskFromList() = runBlocking {
        // arrange
        val fakeTaskName = "Task 1"
        val fakeTaskDescription = "This is the task description for test."
        val fakeTaskModel = TaskModel(id = 0, name = fakeTaskName, description = fakeTaskDescription)

        // act
        taskDao.insertByTask(fakeTaskModel)
        val taskList= taskDao.getAllAsFlow().first()
        taskDao.deleteByTask(taskList.first())
        val taskListUpdated = taskDao.getAllAsFlow().first()

        // assert
        assertThat(taskListUpdated.count()).isEqualTo(0)
        assertThat(taskListUpdated).isEmpty()
    }
}