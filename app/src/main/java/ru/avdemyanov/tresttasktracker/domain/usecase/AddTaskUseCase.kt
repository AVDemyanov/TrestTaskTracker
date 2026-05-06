package ru.avdemyanov.tresttasktracker.domain.usecase

import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(title: String) {
        require(title.isNotBlank() && title.length <= 150) {
            "Название должно быть от 1 до 150 символов"
        }
        repository.addTask(title)
    }
}