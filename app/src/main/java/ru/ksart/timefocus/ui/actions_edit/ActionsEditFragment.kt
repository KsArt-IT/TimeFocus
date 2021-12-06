package ru.ksart.timefocus.ui.actions_edit

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ksart.timefocus.R
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.databinding.FragmentActionsEditBinding
import ru.ksart.timefocus.ui.actions_edit.adapter.ActionsEditGroupAdapter
import ru.ksart.timefocus.ui.actions_edit.adapter.ActionsEditGroupTouchHelper
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

    private val listAdapter
        get() = checkNotNull(binding.recycler.adapter as? ActionsEditGroupAdapter) {
            "Adapter isn't initialized"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("tag153").d("ActionsEditFragment: onCreate=${savedInstanceState == null}")
        if (savedInstanceState == null) args.actionEdit?.let {
            viewModel.setActionName(it, init = true)
        }
    }

    override fun initList() {
        binding.recycler.run {
            adapter = ActionsEditGroupAdapter()
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            ActionsEditGroupTouchHelper(
                viewModel::changeOrderInGroup,
                viewModel::removeFromGroup
            ).attachToRecyclerView(this)
        }
    }

    override fun initListener() {
        binding.run {
            cancelButton.setOnClickListener { back() }
            createButton.setOnClickListener { createActionName() }
            iconColor.setOnClickListener {
                saveActionName()
                viewModel.changeColorIcon()
            }
            iconFile.setOnClickListener {
                saveActionName()
                // переход на выбор иконки
                findNavController().navigate(R.id.action_actionsEditFragment_to_iconsChoiceFragment)
            }
            suspend.setOnCheckedChangeListener { _, isChecked ->
                suspendAll.isEnabled = isChecked
            }
            pomodoro.setOnCheckedChangeListener { _, isChecked ->
                pomodoroLong.isEnabled = isChecked
            }
            group.setOnClickListener { saveActionName() }
            addGroupMember.setOnClickListener {
                showMembersToAddGroup()
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
                            is UiEvent.Success -> {}//setColorIcon(state.data)
                            is UiEvent.Toast -> toast(state.stringId)
                            is UiEvent.Error -> toast(state.message)
                            is UiEvent.Next -> back()
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.uiState.collect { state ->
                        Timber.tag("tag153").d("ActionsEditFragment: uiState")
                        when (state) {
                            is UiState.Success -> {
                                updateUi(state.data)
                                enableView(true)
                            }
                            is UiState.Loading -> enableView(false)
                        }.exhaustive
                    }
                }
                launch {
                    viewModel.groupState.collect { state ->
                        Timber.tag("tag153").d("ActionsEditFragment: groupState")
                        when (state) {
                            is UiState.Success -> {
                                listAdapter.submitList(state.data)
                            }
                            is UiState.Loading -> {}
                        }.exhaustive
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
            ?.run {
                getLiveData(
                    KEY_ADD_ICON,
                    ""
                ).observe(viewLifecycleOwner) { file ->
                    viewModel.setIconFile(file)
                }
                getLiveData<ActionNames>(
                    KEY_ADD_MEMBERS,
                    null
                ).observe(viewLifecycleOwner) { action ->
                    viewModel.addGroupMembers(action)
                }
            }
    }

    private fun updateUi(action: ActionNames) {
        Timber.tag("tag153").d("ActionsEditFragment: initActionName set edit actionName")
        try {
            binding.run {
                setColorIcon(action.color)

                if (action.icon.isNotBlank() && iconFile.tag != action.icon) iconFile.loadSvgFromAsset(
                    action.icon
                )
                iconFile.tag = action.icon

                actionName.setText(action.name)
                suspend.isChecked = action.suspend
                suspendAll.isChecked = action.suspendAll
                pomodoro.isChecked = action.pomodoro
                pomodoroLong.isChecked = action.pomodoroLong

                // группа
                group.isChecked = action.group
                actionNameGroup.isVisible = group.isChecked
                group.setText(if (group.isChecked) R.string.action_group_and_members_text else R.string.action_group_text)
            }
        } catch (e: Exception) {
            toast(R.string.unexpected_error)
            back()
        }
    }

    private fun enableView(enabled: Boolean) {
        binding.run {
            createButton.isEnabled = enabled
            cancelButton.isEnabled = enabled
            actionName.isEnabled = enabled

            suspend.isEnabled = enabled
            suspendAll.isEnabled = suspend.isChecked

            pomodoro.isEnabled = enabled
            pomodoroLong.isEnabled = pomodoro.isChecked

            iconColor.isEnabled = enabled
            iconFile.isEnabled = enabled

            // группа
            group.isEnabled = enabled
            actionNameGroup.isEnabled = enabled
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
                )
            )
        }
    }

    private fun createActionName() {
        saveActionName()
        viewModel.addActionNames()
    }

    private fun setColorIcon(color: Int) {
        Timber.tag("tag153").d("ActionsEditFragment: setColorIcon=$color")
        binding.run {
            iconColor.tag = color
            iconColor.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            iconFile.colorFilter = iconColor.colorFilter
        }
    }

    private fun showMembersToAddGroup() {
        val list = viewModel.getMembersForGroup()
        if (list.isEmpty()) {
            toast(R.string.action_add_group_no_members)
            return
        }
        val action =
            ActionsEditFragmentDirections.actionActionsEditFragmentToActionsForGroupFragment(list)
        findNavController().navigate(action)
    }

    private fun back() {
        findNavController().popBackStack()
    }

    companion object {
        const val KEY_ADD_ICON = "key_add_icon"
        const val KEY_ADD_MEMBERS = "key_add_group_members"
    }

}
