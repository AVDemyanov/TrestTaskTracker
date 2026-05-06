package ru.avdemyanov.tresttasktracker.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.avdemyanov.tresttasktracker.domain.model.Task
import ru.avdemyanov.tresttasktracker.domain.usecase.GetArchivedTasksUseCase

class ArchiveViewModel(
    private val getArchivedTasksUseCase: GetArchivedTasksUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        viewModelScope.launch {
            getArchivedTasksUseCase().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }
}