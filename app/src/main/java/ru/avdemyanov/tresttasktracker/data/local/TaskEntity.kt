package ru.avdemyanov.tresttasktracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val status: ru.avdemyanov.tresttasktracker.data.local.TaskStatus,
    val createdAt: Long = System.currentTimeMillis()
)