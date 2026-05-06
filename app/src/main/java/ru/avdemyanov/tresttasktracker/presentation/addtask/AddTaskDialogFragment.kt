package ru.avdemyanov.tresttasktracker.presentation.addtask


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.avdemyanov.tresttasktracker.databinding.DialogAddTaskBinding

class AddTaskDialogFragment(private val onTaskAdded: (String) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogAddTaskBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddTaskBinding.inflate(requireActivity().layoutInflater)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle("Новая задача")
            .setPositiveButton("Добавить") { _, _ ->
                val title = binding.editTaskTitle.text.toString().trim()
                if (title.isNotEmpty()) {
                    onTaskAdded(title)
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
    }
}