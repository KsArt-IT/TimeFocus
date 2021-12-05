package ru.ksart.timefocus.ui.actions_list

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.ksart.timefocus.databinding.FragmentActionsListBinding
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.ui.actions_list.adapter.ActionsListAdapter
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.BaseFragment
import ru.ksart.timefocus.ui.main.MainFragmentDirections

@AndroidEntryPoint
class ActionsListFragment :
    BaseFragment<FragmentActionsListBinding>(FragmentActionsListBinding::inflate) {

    private val viewModel: ActionsListViewModel by viewModels()

    private val listAdapter
        get() = checkNotNull(binding.recycler.adapter as? ActionsListAdapter) {
            "Adapter isn't initialized"
        }

    override fun initList() {
        binding.recycler.run {
            adapter = ActionsListAdapter(::showItem)
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    override fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is UiEvent.Error -> toast(event.message)
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Success -> {}
                            is UiEvent.Next -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> listAdapter.submitList(state.data)
                            is UiState.Loading -> {}
                        }.exhaustive
                    }
                }
            }
        }
    }

    override fun initListener() {
        binding.addButton.setOnClickListener {
            // переход к фрагменту редактирования, но для создания
            showItem()
//            findNavController().navigate(R.id.action_mainFragment_to_actionsEditFragment)
        }
    }

    private fun showItem(item: ActionNames? = null) {
        // переход к фрагменту редактирования
        val action = MainFragmentDirections.actionMainFragmentToActionsEditFragment(item)
        findNavController().navigate(action)
    }

}
