package ru.ksart.timefocus.ui.icons_choice

import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.ksart.timefocus.databinding.FragmentIconsChoiceBinding
import ru.ksart.timefocus.domain.entities.IconChoice
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.ui.actions_edit.ActionsEditFragment
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.isDarkTheme
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.icons_choice.adapter.IconsChoiceAdapter
import ru.ksart.timefocus.ui.main.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class IconsChoiceFragment :
    BaseFragment<FragmentIconsChoiceBinding>(FragmentIconsChoiceBinding::inflate) {

    private val viewModel: IconsChoiceViewModel by viewModels()

    private val iconsAdapter
        get() = checkNotNull(binding.recycler.adapter as? IconsChoiceAdapter) {
            "Adapter isn't initialized"
        }

    override fun initObserve() {
        Timber.tag("tag153").d("IconsChoiceFragment: start")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is UiEvent.Toast -> toast(event.stringId)
                            is UiEvent.Error -> toast(event.message)
                            is UiEvent.Success -> {}
                            is UiEvent.Next -> {}
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (state) {
                            is UiState.Success -> {
                                loading(false)
                                iconsAdapter.submitList(state.data)
                            }
                            is UiState.Loading -> loading(true)
                        }.exhaustive
                    }
                }
            }
        }
    }

    override fun initList() {
        binding.recycler.run {
            adapter = IconsChoiceAdapter(::selectIcon)
            layoutManager = GridLayoutManager(requireContext().applicationContext, 4)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    private fun selectIcon(icon: IconChoice) {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(
            ActionsEditFragment.KEY_ADD_ICON,
            icon.icon
        )
        navController.popBackStack()
    }

    private fun loading(loading: Boolean) {
        binding.iconsChoiceProgress.isInvisible = loading.not()
    }

}
