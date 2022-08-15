package dev.techpleiades.todocompose.repositories

import dev.techpleiades.todocompose.datasource.daos.TaskDao
import dev.techpleiades.todocompose.datasource.models.TaskModel
import kotlinx.coroutines.flow.Flow

class TaskRepository (
    private val taskDao: TaskDao
) {
    fun getAllAsFlow(): Flow<List<TaskModel>> = taskDao.getAllAsFlow()
    suspend fun findByTaskId(id: Long) = taskDao.get(id)
    suspend fun insert(task: TaskModel) = taskDao.insertByTask(task)
    suspend fun update(task: TaskModel) = taskDao.updateByTask(task)
    suspend fun delete(task: TaskModel) = taskDao.deleteByTask(task)
    suspend fun deleteAll() = taskDao.deleteAll()
}