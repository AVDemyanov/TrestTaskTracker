package ru.avdemyanov.tresttasktracker.presentation.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.avdemyanov.tresttasktracker.TaskTrackerApplication
import ru.avdemyanov.tresttasktracker.databinding.FragmentArchiveBinding
import ru.avdemyanov.tresttasktracker.domain.model.Task
import ru.avdemyanov.tresttasktracker.domain.usecase.GetArchivedTasksUseCase
import ru.avdemyanov.tresttasktracker.presentation.utils.ViewModelFactory

class ArchiveFragment : Fragment() {

    private var _binding: FragmentArchiveBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ArchiveViewModel
    private lateinit var adapter: ArchivedTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = requireActivity().application as TaskTrackerApplication
        val factory = ViewModelFactory(
            mapOf(
                ArchiveViewModel::class.java to {
                    ArchiveViewModel(app.getArchivedTasksUseCase)
                }
            )
        )
        viewModel = ViewModelProvider(this, factory)[ArchiveViewModel::class.java]

        setupRecyclerView()
        observeTasks()
    }

    private fun setupRecyclerView() {
        adapter = ArchivedTaskAdapter()
        binding.recyclerViewArchived.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewArchived.adapter = adapter
    }

    private fun observeTasks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tasks.collectLatest { tasks ->
                adapter.submitList(tasks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}