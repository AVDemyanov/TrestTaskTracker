package ru.avdemyanov.tresttasktracker.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.avdemyanov.tresttasktracker.databinding.ItemTaskBinding
import ru.avdemyanov.tresttasktracker.domain.model.Task

class TaskAdapter(
    private val onCompleteClick: (Long) -> Unit,
    private val onDeleteClick: (Long) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var tasks: List<Task> = emptyList()
        private set

    fun submitList(list: List<Task>) {
        tasks = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textTaskTitle.text = task.title
            binding.checkBoxComplete.isChecked = false

            binding.checkBoxComplete.setOnCheckedChangeListener(null)
            binding.checkBoxComplete.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onCompleteClick(task.id)
                }
            }

            binding.buttonDelete.setOnClickListener {
                onDeleteClick(task.id)
            }
        }
    }
}