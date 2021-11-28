package ru.ksart.timefocus.ui.action_edit

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.FragmentActionsEditBinding
import ru.ksart.timefocus.model.data.UiEvent
import ru.ksart.timefocus.model.db.models.ActionNames
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset
import ru.ksart.timefocus.ui.extension.toast
import ru.ksart.timefocus.ui.main.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class ActionsEditFragment :
    BaseFragment<FragmentActionsEditBinding>(FragmentActionsEditBinding::inflate) {

    private val viewModel: ActionsEditViewModel by viewModels()

    private val args: ActionsEditFragmentArgs by navArgs()

    private var isUpdateActionName = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("tag153").d("ActionsEditFragment: onCreate=$savedInstanceState")
        if (savedInstanceState == null) args.actionEdit?.let { viewModel.setActionName(it) }
        else isUpdateActionName = false
    }

    override fun initListener() {
        binding.run {
            cancelButton.setOnClickListener { back() }
            createButton.setOnClickListener { createActionName() }
            iconColor.setOnClickListener { viewModel.changeColorIcon() }
            iconFile.setOnClickListener {
                // переход на выбор иконки
                findNavController().navigate(R.id.action_actionsEditFragment_to_iconsChoiceFragment)
            }
            suspend.setOnCheckedChangeListener { _, isChecked ->
                suspendAll.isEnabled = isChecked
            }
            pomodoro.setOnCheckedChangeListener { _, isChecked ->
                pomodoroLong.isEnabled = isChecked
            }
        }
    }

    override fun initObserve() {
        initObserveNavigation()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collect { state ->
                        when (state) {
                            is UiEvent.Success -> setColorIcon(state.data)
                            is UiEvent.Loading -> enableView(state.isLoading)
                            is UiEvent.Toast -> toast(state.stringId)
                            is UiEvent.Error -> toast(state.message)
                            is UiEvent.Next -> back()
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiState.collect { state ->
                        Timber.tag("tag153").d("ActionsEditFragment: uiState=${state.data?.id}")
                        state.data?.let(::updateUi)
                        state.isLoading?.let { enableView(it.not()) }
                    }
                }
            }
        }
    }

    private fun initObserveNavigation() {
        // навигация с получением результата
        val navController = findNavController()
        // We use a String here, but any type that can be put in a Bundle is supported
        navController.currentBackStackEntry?.savedStateHandle
            ?.getLiveData(KEY_ADD_ICON, "")?.observe(viewLifecycleOwner) { file ->
                if (file.isNotBlank()) viewModel.setIconFile(file)
            }
    }

    private fun updateUi(action: ActionNames) {
        Timber.tag("tag153").d("ActionsEditFragment: initActionName set edit actionName")
        try {
            binding.run {
                setColorIcon(action.color)

                if (action.icon.isNotBlank()) iconFile.loadSvgFromAsset(action.icon)
                iconFile.tag = action.icon

                if (isUpdateActionName.not()) return
                isUpdateActionName = false

                actionName.setText(action.name)
                group.isChecked = action.group

                suspend.isChecked = action.suspend
                suspendAll.isChecked = action.suspendAll

                pomodoro.isChecked = action.pomodoro
                pomodoroLong.isChecked = action.pomodoroLong
            }
        } catch (e: Exception) {
            back()
        }
    }

    private fun saveActionName() {
        Timber.tag("tag153").d("ActionsEditFragment: saveActionName")
        binding.run {
            viewModel.setActionName(
                ActionNames(
                    name = actionName.text.toString(),
                    group = group.isChecked,
                    suspend = suspend.isChecked,
                    suspendAll = suspendAll.isChecked,
                    pomodoro = pomodoro.isChecked,
                    pomodoroLong = pomodoroLong.isChecked,
                    color = iconColor.tag as? Int ?: Color.BLACK,
                    icon = iconFile.tag as? String ?: "",
                ),
                copy = true
            )
        }
    }

    private fun createActionName() {
        saveActionName()
        viewModel.addActionNames()
    }

    private fun enableView(enabled: Boolean) {
        binding.run {
            createButton.isEnabled = enabled
            cancelButton.isEnabled = enabled
            actionName.isEnabled = enabled

            group.isEnabled = enabled

            suspend.isEnabled = enabled
            suspendAll.isEnabled = suspend.isChecked

            pomodoro.isEnabled = enabled
            pomodoroLong.isEnabled = pomodoro.isChecked

            iconColor.isEnabled = enabled
            iconFile.isEnabled = enabled
        }
    }

    private fun setColorIcon(color: Int) {
        Timber.tag("tag153").d("ActionsEditFragment: setColorIcon=$color")
        binding.run {
            iconColor.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            iconColor.tag = color
            iconFile.colorFilter = iconColor.colorFilter
        }
    }

    private fun back() {
        findNavController().popBackStack()
    }

    companion object {
        const val KEY_ADD_ICON = "key_add_icon"
    }

}
