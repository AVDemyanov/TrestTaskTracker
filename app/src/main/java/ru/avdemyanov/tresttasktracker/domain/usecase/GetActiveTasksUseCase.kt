package ru.avdemyanov.tresttasktracker.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.avdemyanov.tresttasktracker.domain.model.Task
import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository

class GetActiveTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> = repository.getActiveTasks()
}