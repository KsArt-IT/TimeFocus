package ru.ksart.timefocus.ui.actions_list_group

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState

class ActionsForGroupViewModel : ViewModel() {

    private val _groupState =
        MutableStateFlow<UiState<List<ActionNames>>>(UiState.Success(emptyList()))
    val groupState = _groupState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setMembersForGroup(list: List<ActionNames>) {
        _groupState.value = UiState.Success(list)
    }
}
