package ru.avdemyanov.tresttasktracker.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.avdemyanov.tresttasktracker.domain.model.Task

interface TaskRepository {
    fun getActiveTasks(): Flow<List<Task>>
    fun getArchivedTasks(): Flow<List<Task>>
    suspend fun addTask(title: String, description: String)
    suspend fun completeTask(taskId: Long)
    suspend fun deleteTask(taskId: Long)
}