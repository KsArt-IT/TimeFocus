package ru.ksart.timefocus.ui.history

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
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.databinding.FragmentHistoryBinding
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.history.adapter.HistoryAdapter
import ru.ksart.timefocus.ui.main.BaseFragment
import ru.ksart.timefocus.ui.main.MainFragmentDirections
import timber.log.Timber

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private val viewModel: HistoryViewModel by viewModels()

    private val historyAdapter
        get() = checkNotNull(binding.recycler.adapter as? HistoryAdapter) {
            "Adapter isn't initialized"
        }

    override fun initListener() {
        binding.run {
            dateBackButton.setOnClickListener { viewModel.dateBack() }
            dateForwardButton.setOnClickListener { viewModel.dateForward() }
        }
    }

    override fun initList() {
        binding.recycler.run {
            adapter = HistoryAdapter(viewModel::accept.invoke())
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    override fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> {
                                Timber.tag("tag153")
                                    .d("HistoryFragment uiState list=${state.data.intervals.size}")
                                historyAdapter.submitList(state.data.intervals)
                                binding.run {
                                    title.text = state.data.date
                                    subTitle.text = state.data.timeSum
                                }
                            }
                            is UiState.Loading -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is UiEvent.Success -> editInterval(event.data)
                            is UiEvent.Error -> toast(event.message)
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Next -> {}
                        }.exhaustive
                    }
                }
            }
        }
    }

    private fun editInterval(item: ActionIntervalsWithInfo) {
        // переход к фрагменту редактирования группы интервалов
        // группируется не по названию, а по общему action, которое уже может быть удалено
        val action = MainFragmentDirections.actionMainFragmentToHistoryGroupFragment(item.actionId)
        findNavController().navigate(action)
    }
}
