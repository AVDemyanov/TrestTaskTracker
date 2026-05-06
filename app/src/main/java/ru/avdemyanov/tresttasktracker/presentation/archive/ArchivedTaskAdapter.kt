package ru.avdemyanov.tresttasktracker.presentation.archive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.avdemyanov.tresttasktracker.databinding.ItemArchivedTaskBinding
import ru.avdemyanov.tresttasktracker.domain.model.Task
import ru.avdemyanov.tresttasktracker.domain.model.TaskStatus

class ArchivedTaskAdapter : RecyclerView.Adapter<ArchivedTaskAdapter.ArchiveViewHolder>() {

    private var tasks = listOf<Task>()

    fun submitList(list: List<Task>) {
        tasks = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val binding = ItemArchivedTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class ArchiveViewHolder(private val binding: ItemArchivedTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textTaskTitle.text = task.title
            val iconRes = when (task.status) {
                TaskStatus.COMPLETED -> android.R.drawable.checkbox_on_background
                TaskStatus.DELETED -> android.R.drawable.ic_menu_delete
                else -> android.R.drawable.ic_menu_close_clear_cancel
            }
            binding.imageStatus.setImageResource(iconRes)
        }
    }
}