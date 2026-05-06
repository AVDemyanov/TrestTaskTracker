package ru.avdemyanov.tresttasktracker.domain.usecase

import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(title: String) {
        repository.addTask(title)
    }
}