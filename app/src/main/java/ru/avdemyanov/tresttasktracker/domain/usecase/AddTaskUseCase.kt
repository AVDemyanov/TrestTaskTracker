package ru.avdemyanov.tresttasktracker.domain.usecase


import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(title: String, description: String) {
        require(title.isNotBlank() && title.length <= 150) {
            "Название задачи должно быть от 1 до 150 символов"
        }
        require(description.length <= 1000) {
            "Описание задачи должно быть менее 1000 символов"
        }
        repository.addTask(title, description)
    }
}