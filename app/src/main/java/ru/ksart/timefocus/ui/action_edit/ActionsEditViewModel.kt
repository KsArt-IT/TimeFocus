package ru.ksart.timefocus.ui.action_edit

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
import ru.ksart.timefocus.domain.usecase.actions_edit.CreateActionNamesUseCase
import ru.ksart.timefocus.domain.usecase.actions_edit.UpdateActionNamesUseCase
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.data.db.models.ActionNames
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionsEditViewModel @Inject constructor(
    private val createActionNames: CreateActionNamesUseCase,
    private val updateActionNames: UpdateActionNamesUseCase,
) : ViewModel() {

    private var actionNames = ActionNames(name = "", icon = "")

    private val _uiState = MutableStateFlow<UiState<ActionNames>>(UiState.Success(actionNames))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Int>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setActionName(action: ActionNames, copy: Boolean = false) {
        actionNames = if (copy) {
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
        } else {
            action
        }
        _uiState.value = UiState.Success(actionNames)
        Timber.tag("tag153").d("ActionsEditViewModel: setActionName id=${actionNames.id}")
    }

    fun addActionNames() {
        viewModelScope.launch {
            try {
                Timber.tag("tag153").d("ActionsEditViewModel: addActionNames id=${actionNames.id}")
                if (actionNames.name.isNotBlank() && actionNames.icon.isNotBlank()) {
                    if (actionNames.id == 0L) createActionNames(actionNames)
                    else updateActionNames(actionNames)
                    _uiEvent.send(UiEvent.Next())
                } else {
                    _uiEvent.send(UiEvent.Toast(R.string.action_add_no_name))
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
/*

    fun setColorIcon(color: Int) {
        actionNames = actionNames.copy(color = color)
        sendUiEvent(UiEvent.Success(color))
    }
*/

    fun setIconFile(file: String) {
        actionNames = actionNames.copy(icon = file)
        _uiState.value = UiState.Success(actionNames)
    }

    fun changeColorIcon() {
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
        sendUiEvent(UiEvent.Success(color))
        _uiState.value = UiState.Success(actionNames)
    }

    private fun sendUiEvent(state: UiEvent<Int>) {
        viewModelScope.launch { _uiEvent.send(state) }
    }
}
