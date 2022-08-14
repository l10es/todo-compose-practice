package dev.techpleiades.todocompose.datasource.daos

import androidx.room.*
import dev.techpleiades.todocompose.datasource.models.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertByTask(taskModel: TaskModel)

    @Delete
    suspend fun deleteByTask(taskModel: TaskModel)

    @Update
    suspend fun updateByTask(taskModel: TaskModel)

    @Query("DELETE FROM TASK")
    suspend fun deleteAll()

    @Query("SELECT * FROM TASK WHERE ID = :id")
    suspend fun get(id: Long): TaskModel?

    @Query("SELECT * FROM TASK")
    fun getAllAsFlow(): Flow<List<TaskModel>>
}