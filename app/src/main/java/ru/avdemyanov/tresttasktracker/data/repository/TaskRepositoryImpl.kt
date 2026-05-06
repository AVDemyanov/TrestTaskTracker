package ru.avdemyanov.tresttasktracker.data.repository

import ru.avdemyanov.tresttasktracker.data.local.TaskDao
import ru.avdemyanov.tresttasktracker.data.local.TaskEntity
import ru.avdemyanov.tresttasktracker.data.local.TaskStatus as EntityStatus  // алиас для data
import ru.avdemyanov.tresttasktracker.domain.model.Task
import ru.avdemyanov.tresttasktracker.domain.model.TaskStatus                  // domain-версия
import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getActiveTasks(): Flow<List<Task>> =
        taskDao.getActiveTasks().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getArchivedTasks(): Flow<List<Task>> =
        taskDao.getArchivedTasks().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addTask(title: String) {
        val task = TaskEntity(
            title = title,
            status = EntityStatus.ACTIVE   // явно из data.local
        )
        taskDao.insert(task)
    }

    override suspend fun completeTask(taskId: Long) {
        taskDao.updateStatus(taskId, EntityStatus.COMPLETED)
    }

    override suspend fun deleteTask(taskId: Long) {
        taskDao.updateStatus(taskId, EntityStatus.DELETED)
    }

    private fun TaskEntity.toDomain(): Task = Task(
        id = id,
        title = title,
        status = when (status) {          // status здесь типа EntityStatus
            EntityStatus.ACTIVE -> TaskStatus.ACTIVE      // domain-версия
            EntityStatus.COMPLETED -> TaskStatus.COMPLETED
            EntityStatus.DELETED -> TaskStatus.DELETED
        },
        createdAt = createdAt
    )
}