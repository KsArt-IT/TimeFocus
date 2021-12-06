package ru.ksart.timefocus.ui.actions_list_group

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.databinding.FragmentActionsListForGroupBinding
import ru.ksart.timefocus.ui.actions_edit.ActionsEditFragment
import ru.ksart.timefocus.ui.actions_list.adapter.ActionsListAdapter
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.BaseFragment

class ActionsForGroupFragment :
    BaseFragment<FragmentActionsListForGroupBinding>(FragmentActionsListForGroupBinding::inflate) {

    private val viewModel: ActionsForGroupViewModel by viewModels()

    private val args: ActionsForGroupFragmentArgs by navArgs()

    private val listAdapter
        get() = checkNotNull(binding.recycler.adapter as? ActionsListAdapter) {
            "Adapter isn't initialized"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) args.membersForGroup.let { viewModel.setMembersForGroup(it.toList()) }
    }

    override fun initList() {
        binding.recycler.run {
            adapter = ActionsListAdapter(::selectItem)
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
                    viewModel.groupState.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> listAdapter.submitList(state.data)
                            is UiState.Loading -> {}
                        }.exhaustive
                    }
                }
            }
        }
    }

    private fun selectItem(item: ActionNames) {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(
            ActionsEditFragment.KEY_ADD_MEMBERS,
            item
        )
        navController.popBackStack()
    }

}
