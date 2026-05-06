package ru.avdemyanov.tresttasktracker.presentation.addtask


import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
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
                when {
                    title.isEmpty() -> {
                        Toast.makeText(requireContext(),"Название не может быть пустым", Toast.LENGTH_SHORT).show()
                    }
                    title.length > 150 -> {
                        Toast.makeText(requireContext(),"Название должно быть короче 150 символов",
                            Toast.LENGTH_SHORT).show()
                    }
                    else -> onTaskAdded(title)
                }
                }
            .setNegativeButton("Отмена", null)
            .create()
    }
}