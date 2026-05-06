package ru.avdemyanov.tresttasktracker.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val creators: Map<Class<out ViewModel>, () -> ViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value
        requireNotNull(creator) { "No ViewModel creator for ${modelClass.name}" }
        @Suppress("UNCHECKED_CAST")
        return creator() as T
    }
}