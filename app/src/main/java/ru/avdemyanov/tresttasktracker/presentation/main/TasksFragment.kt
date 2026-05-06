package ru.avdemyanov.tresttasktracker.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.avdemyanov.tresttasktracker.R
import ru.avdemyanov.tresttasktracker.TaskTrackerApplication
import ru.avdemyanov.tresttasktracker.databinding.FragmentTasksBinding
import ru.avdemyanov.tresttasktracker.presentation.addtask.AddTaskDialogFragment
import ru.avdemyanov.tresttasktracker.presentation.utils.ViewModelFactory

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as TaskTrackerApplication
        val factory = ViewModelFactory(
            mapOf(
                MainViewModel::class.java to {
                    MainViewModel(
                        getActiveTasksUseCase = app.getActiveTasksUseCase,
                        addTaskUseCase = app.addTaskUseCase,
                        completeTaskUseCase = app.completeTaskUseCase,
                        deleteTaskUseCase = app.deleteTaskUseCase
                    )
                }
            )
        )
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setupRecyclerView()
        setupSwipeToDelete()
        observeTasks()

        binding.buttonAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        binding.buttonArchive.setOnClickListener {
            view.findNavController().navigate(R.id.action_tasks_to_archive)
        }
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onCompleteClick = { taskId -> viewModel.completeTask(taskId) },
            onDeleteClick = { taskId -> viewModel.deleteTask(taskId) }
        )
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTasks.adapter = adapter
    }

    private fun setupSwipeToDelete() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.tasks.getOrNull(position) ?: return
                viewModel.deleteTask(task.id)
                Toast.makeText(requireContext(), "Задача удалена", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerViewTasks)
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collectLatest { tasks ->
                adapter.submitList(tasks)
            }
        }
    }

    private fun showAddTaskDialog() {
        val dialog = AddTaskDialogFragment { title ->
            if (title.isNotBlank()) {
                viewModel.addTask(title)
            } else {
                Toast.makeText(requireContext(), "Введите название задачи", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show(parentFragmentManager, "AddTaskDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
