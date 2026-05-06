package ru.avdemyanov.tresttasktracker.domain.usecase

import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long) {
        repository.deleteTask(taskId)
    }
}