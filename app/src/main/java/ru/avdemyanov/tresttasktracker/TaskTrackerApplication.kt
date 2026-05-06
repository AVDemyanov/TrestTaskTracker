package ru.avdemyanov.tresttasktracker

import android.app.Application
import ru.avdemyanov.tresttasktracker.data.local.TaskDatabase
import ru.avdemyanov.tresttasktracker.domain.repository.TaskRepository
import ru.avdemyanov.tresttasktracker.data.repository.TaskRepositoryImpl
import ru.avdemyanov.tresttasktracker.domain.usecase.AddTaskUseCase
import ru.avdemyanov.tresttasktracker.domain.usecase.CompleteTaskUseCase
import ru.avdemyanov.tresttasktracker.domain.usecase.DeleteTaskUseCase
import ru.avdemyanov.tresttasktracker.domain.usecase.GetActiveTasksUseCase
import ru.avdemyanov.tresttasktracker.domain.usecase.GetArchivedTasksUseCase

class TaskTrackerApplication: Application() {
    private val database by lazy { TaskDatabase.getInstance(this) }
    private val taskDao by lazy { database.taskDao() }


    private val taskRepository: TaskRepository by lazy {
        TaskRepositoryImpl(taskDao)
    }
    val getActiveTasksUseCase by lazy { GetActiveTasksUseCase(taskRepository) }
    val getArchivedTasksUseCase by lazy { GetArchivedTasksUseCase(taskRepository) }
    val addTaskUseCase by lazy { AddTaskUseCase(taskRepository) }
    val completeTaskUseCase by lazy { CompleteTaskUseCase(taskRepository) }
    val deleteTaskUseCase by lazy { DeleteTaskUseCase(taskRepository) }
}