package ru.avdemyanov.tresttasktracker.domain.model

data class Task(
    val id: Long,
    val title: String,
    val status: TaskStatus,
    val createdAt: Long,
    val description: String
)

enum class TaskStatus {
    ACTIVE, COMPLETED, DELETED
}