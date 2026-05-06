package ru.avdemyanov.tresttasktracker.domain.usecase

import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository

class CompleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long) {
        repository.completeTask(taskId)
    }
}