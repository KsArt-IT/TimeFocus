package ru.ksart.timefocus.ui.actions_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.actions_list.GetActionsListUseCase
import ru.ksart.timefocus.ui.extension.exhaustive
import javax.inject.Inject

@HiltViewModel
class ActionsListViewModel @Inject constructor(
    private val getActionsList: GetActionsListUseCase,
) : ViewModel() {

    val uiState: StateFlow<UiState<List<ActionNames>>>

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        uiState = getActionsList.observe()
            .mapNotNull { result ->
                when (result) {
                    is Results.Success -> {
//                        Timber.tag("tag153").d("ActionsListViewModel list=${result.data.size}")
                        UiState.Success(result.data)
                    }
                    is Results.Error -> {
                        _uiEvent.send(UiEvent.Error(result.message))
                        null
                    }
                }.exhaustive
            }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState.Loading
            )
    }

}
