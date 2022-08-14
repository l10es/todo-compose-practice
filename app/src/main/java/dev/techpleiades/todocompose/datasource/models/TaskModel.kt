package dev.techpleiades.todocompose.datasource.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TASK")
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Long = 0L,

    @ColumnInfo(name = "NAME")
    val name: String = "",

    @ColumnInfo(name = "DESCRIPTION")
    val description: String = ""
)
