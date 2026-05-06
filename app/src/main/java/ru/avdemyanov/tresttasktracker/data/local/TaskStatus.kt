package ru.avdemyanov.tresttasktracker.data.local

import androidx.room.TypeConverter

enum class TaskStatus {
    ACTIVE, COMPLETED, DELETED
}
class TaskStatusConverter {
    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(status: String): TaskStatus = TaskStatus.valueOf(status)
}