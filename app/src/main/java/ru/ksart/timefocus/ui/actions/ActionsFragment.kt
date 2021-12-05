package ru.ksart.timefocus.ui.actions

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ksart.timefocus.databinding.FragmentActionsBinding
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.ui.actions.adapter.ActionNamesAdapter
import ru.ksart.timefocus.ui.actions.adapter.ActionsAdapter
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class ActionsFragment : BaseFragment<FragmentActionsBinding>(FragmentActionsBinding::inflate) {

    private val viewModel: ActionsViewModel by viewModels()

    private val actionsAdapter
        get() = checkNotNull(binding.actionsList.adapter as? ActionsAdapter) {
            "Adapter isn't initialized"
        }
    private val actionNamesAdapter
        get() = checkNotNull(binding.actionNamesList.adapter as? ActionNamesAdapter) {
            "Adapter isn't initialized"
        }

    override fun initObserve() {
        Timber.tag("tag153").d("ActionsFragment: initObserve")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiStateAction.collect { state ->
                        when(state) {
                            is UiState.Success -> actionsAdapter.submitList(state.data)
                            is UiState.Loading -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiState.collect { state ->
                        Timber.tag("tag153").d("ActionsFragment: uiState")
                        when(state) {
                            is UiState.Success -> actionNamesAdapter.submitList(state.data)
                            is UiState.Loading -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiEvent.collect { event ->
                        Timber.tag("tag153").d("ActionsFragment: uiState")
                        when(event) {
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Error -> toast(event.message)
                            is UiEvent.Success -> {}
                            is UiEvent.Next -> {}
                        }.exhaustive
                    }
                }
            }
        }
    }

    override fun initList() {
        Timber.tag("tag153").d("ActionsFragment: initList")
        binding.actionsList.run {
            adapter = ActionsAdapter(viewModel::uiAction.invoke())
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        binding.actionNamesList.run {
            adapter = ActionNamesAdapter(viewModel::uiAction.invoke())
            layoutManager = GridLayoutManager(requireContext().applicationContext, 4)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }
}
