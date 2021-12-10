package ru.ksart.timefocus.ui.actions_edit

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.ksart.timefocus.R
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.ActionMode
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.actions_edit.CreateActionNamesUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.GetActionNamesByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.GetActionNamesByNameUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.GetActionNamesWithoutGroupUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.RemoveGroupIdFromActionNamesByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.UpdateActionNamesUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionsEditViewModel @Inject constructor(
    private val createActionNames: CreateActionNamesUseCase,
    private val updateActionNames: UpdateActionNamesUseCase,
    private val getActionNamesWithoutGroup: GetActionNamesWithoutGroupUseCase,
    private val getActionNamesByGroupId: GetActionNamesByGroupIdUseCase,
    private val getActionNamesByName: GetActionNamesByNameUseCase,
    private val removeGroupIdFromActionNamesByGroupId: RemoveGroupIdFromActionNamesByGroupIdUseCase,
) : ViewModel() {

    private var actionNames = ActionNames(name = "", icon = "")

    private val _uiState = MutableStateFlow<UiState<ActionNames>>(UiState.Success(actionNames))
    val uiState = _uiState.asStateFlow()

    // члены группы
    private var groupMembers = mutableListOf<ActionNames>()

    // члены которых можно взять в группу
    private var membersForGroup = mutableListOf<ActionNames>()

    private val _groupState =
        MutableStateFlow<UiState<List<ActionNames>>>(UiState.Success(emptyList()))
    val groupState = _groupState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        // запросим все записи без группы
        requestMembersForGroup()
    }

    fun setActionName(action: ActionNames, init: Boolean = false) {
        viewModelScope.launch {
            actionNames = if (init) {
                // запросим членов группы
                requestGroupMembersByGroupId(action.id)
                action
            } else {
                actionNames.copy(
                    name = action.name,
                    description = action.description,
                    group = action.group,
                    groupId = action.groupId,
                    suspend = action.suspend,
                    suspendAll = action.suspendAll,
                    pomodoro = action.pomodoro,
                    pomodoroLong = action.pomodoroLong,
                    pomodoroSwitchId = action.pomodoroSwitchId,
                    color = action.color,
                    icon = action.icon,
                    number = action.number,
                )
            }
            updateUiState()
            Timber.tag("tag153").d("ActionsEditViewModel: setActionName id=${actionNames.id}")
        }
    }

    fun addActionNames() {
        viewModelScope.launch {
            try {
                Timber.tag("tag153").d("ActionsEditViewModel: addActionNames id=${actionNames.id}")
                Timber.tag("tag153")
                    .d("ActionsEditViewModel: addActionNames -----------------------")
                if (actionNames.name.isNotBlank() && actionNames.icon.isNotBlank()) {
                    actionNames = actionNames.copy(
                        groupCount = if (actionNames.group && groupMembers.isNotEmpty()) groupMembers.size else 0
                    )
                    if (actionNames.id == 0L) createActionNames(actionNames)
                    else updateActionNames(actionNames)
                    // получить groupId если его нет
                    val groupId = if (actionNames.id > 0) actionNames.id
                    else {
                        actionNames = getActionNamesByName(actionNames.name)
                        actionNames.id
                    }
                    Timber.tag("tag153").d("ActionsEditViewModel: addActionNames groupId=$groupId")
                    // очистим данные о группе по groupId и обновим
                    removeGroupIdFromActionNamesByGroupId(groupId)
                    // сохранить группу
                    if (actionNames.group && groupMembers.isNotEmpty()) {
                        if (groupId > 0) {
                            // обновим
                            groupMembers.forEach { action ->
                                Timber.tag("tag153")
                                    .d("ActionsEditViewModel: addActionNames group add id=${action.id}")
                                updateActionNames(action.copy(groupId = groupId))
                            }
                        } else {
                            _uiEvent.send(UiEvent.Toast(R.string.action_add_group_error))
                        }
                    }
                    _uiEvent.send(UiEvent.Next())
                } else {
                    _uiEvent.send(UiEvent.Toast(R.string.action_add_no_name))
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    fun setIconFile(file: String) {
        if (file.isBlank()) return
        actionNames = actionNames.copy(icon = file)
        updateUiState()
    }

    fun changeColorIcon() {
        viewModelScope.launch {
            val color = when (actionNames.color) {
                Color.BLACK -> Color.WHITE
                Color.WHITE -> Color.RED
                Color.RED -> Color.parseColor("#FFF06400")
                Color.parseColor("#FFF06400") -> Color.YELLOW
                Color.YELLOW -> Color.GREEN
                Color.GREEN -> Color.CYAN
                Color.CYAN -> Color.BLUE
                Color.BLUE -> Color.parseColor("#FF6400F0")
                Color.parseColor("#FF6400F0") -> Color.DKGRAY
                Color.GRAY -> Color.GRAY
                else -> Color.BLACK
            }
            actionNames = actionNames.copy(color = color)
            Timber.tag("tag153").d("ActionsEditViewModel: color=$color")
            updateUiState()
        }
    }

    private fun requestMembersForGroup() {
        viewModelScope.launch {
            Timber.tag("tag153").d("ActionsEditViewModel: requestMembersForGroup")
            when (val result = getActionNamesWithoutGroup()) {
                is Results.Success -> {
                    membersForGroup = result.data.toMutableList()
                    Timber.tag("tag153")
                        .d("ActionsEditViewModel: requestMembersForGroup membersForGroup=${membersForGroup.size}")
                }
                is Results.Error -> {
                    _uiEvent.send(UiEvent.Error(result.message))
                }
            }
        }
    }

    private suspend fun requestGroupMembersByGroupId(groupId: Long) {
        Timber.tag("tag153").d("ActionsEditViewModel: requestGroupMembersByGroupId")
        when (val result = getActionNamesByGroupId(groupId)) {
            is Results.Success -> {
                groupMembers = result.data.toMutableList()
                Timber.tag("tag153")
                    .d("ActionsEditViewModel: requestGroupMembersByGroupId groupMembers=${groupMembers.size}")
                if (membersForGroup.isNotEmpty()) {
                    // удалим совпадения с группой
                    if (groupMembers.isNotEmpty()) {
                        Timber.tag("tag153")
                            .d("ActionsEditViewModel: requestGroupMembersByGroupId membersForGroup - groupMembers")
                        membersForGroup.removeAll(groupMembers)
                    }
                    // удалим текущий groupId
                    Timber.tag("tag153")
                        .d("ActionsEditViewModel: requestGroupMembersByGroupId membersForGroup -$groupId")
                    val item = membersForGroup.firstOrNull { it.id == groupId }
                    item?.let { membersForGroup.remove(it) }
                }
            }
            is Results.Error -> {
                _uiEvent.send(UiEvent.Error(result.message))
            }
        }
        updateGroupState()
    }

    fun getMembersForGroup() = membersForGroup.toTypedArray()

    fun addGroupMembers(actionName: ActionNames?) {
        Timber.tag("tag153").d("ActionsEditViewModel: addGroupMembers")
        actionName ?: return
        viewModelScope.launch {
            // удалим из свободных
            if (membersForGroup.isNotEmpty()) membersForGroup.remove(actionName)
            // добавим к группе и обновим
            if (groupMembers.contains(actionName).not()) groupMembers.add(actionName)
            updateGroupState()
        }
    }

    fun changeOrderInGroup(actionNames: ActionNames) {

    }

    fun removeFromGroup(actionName: ActionNames) {
        viewModelScope.launch {
            try {
                groupMembers.remove(actionName)
                membersForGroup.add(actionName)
                _groupState.value = UiState.Success(groupMembers.toList())
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }

    private fun updateUiState() {
        _uiState.value = UiState.Success(actionNames)
        Timber.tag("tag153")
            .d("ActionsEditViewModel: color=${(uiState.value as UiState.Success).data.color}")

    }

    private fun updateGroupState() {
        _groupState.value = UiState.Success(groupMembers.toList())
    }

    fun changeMode() {
        viewModelScope.launch {
            val mode = when (actionNames.mode) {
                ActionMode.NOTHING -> ActionMode.STRICT
                ActionMode.STRICT -> ActionMode.PAUSE
                ActionMode.PAUSE -> ActionMode.NOTHING
            }
            actionNames = actionNames.copy(mode = mode)
            Timber.tag("tag153").d("ActionsEditViewModel: mode=$mode")
            updateUiState()
        }
    }
}
