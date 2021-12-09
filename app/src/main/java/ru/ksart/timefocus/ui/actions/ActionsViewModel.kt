package ru.ksart.timefocus.ui.actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.actions.CreateActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionNamesGroupByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionNamesWithoutGroupUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionsWithInfoUseCase
import ru.ksart.timefocus.domain.usecase.actions.PauseTimerUseCase
import ru.ksart.timefocus.domain.usecase.actions.StartTimerUseCase
import ru.ksart.timefocus.domain.usecase.actions.StopActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.StopTimerUseCase
import ru.ksart.timefocus.domain.usecase.actions.UpdateActionStatusUseCase
import ru.ksart.timefocus.ui.extension.exhaustive
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionsViewModel @Inject constructor(
    private val getActionsWithInfo: GetActionsWithInfoUseCase,

    private val createAction: CreateActionUseCase,
    private val updateActionStatus: UpdateActionStatusUseCase,
    private val stopAction: StopActionUseCase,

    private val startTimer: StartTimerUseCase,
    private val pauseTimer: PauseTimerUseCase,
    private val stopTimer: StopTimerUseCase,

    private val getActionsNames: GetActionNamesWithoutGroupUseCase,
    private val getActionsNamesByGroupId: GetActionNamesGroupByGroupIdUseCase,
) : ViewModel() {

    private val actionNamesGroupId = MutableStateFlow<Long?>(null)
    private var actionNamesGroupName = ""
    val uiState: StateFlow<UiState<List<ActionNames>>>
    val uiStateAction: StateFlow<UiState<List<ActionWithInfo>>>

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiAction: (UiAction<ActionWithInfo>) -> Unit

    init {
        // _uiState
        uiState = actionNamesGroupId.flatMapLatest { groupId ->
            groupId?.let { getActionsNamesByGroupId.observe(it) } ?: getActionsNames.observe()
        }.mapNotNull { result ->
            when (result) {
                is Results.Success -> UiState.Success(result.data)
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
        // _uiStateAction
        uiStateAction = getActionsWithInfo.observe()
            .mapNotNull { result ->
                when (result) {
                    is Results.Success -> {
                        updateTimersFromActions(result.data)
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

        uiAction = { action ->
            viewModelScope.launch {
                Timber.tag("tag153").d("ActionsViewModel: accept action=$action")
                when (action) {
                    is UiAction.Select -> selectActionNames(action.actionNames)
                    is UiAction.Click -> changeActionStatus(action.data)
                    is UiAction.Stop -> stopActionAndTimer(action.data)
                }.exhaustive
            }
        }

    }

    private fun updateTimersFromActions(list: List<ActionWithInfo>) {
        viewModelScope.launch {
            Timber.tag("tag153").d("ActionsViewModel: actionsWithInfo = ${list.size}")
            list.forEach { action ->
                Timber.tag("tag153")
                    .d("ActionsViewModel: actionsWithInfo start timer = ${action.id}")
                when (action.status) {
                    ActionStatus.ACTIVE -> startTimer(action)
                    ActionStatus.PAUSE -> pauseTimer(action)
                    ActionStatus.STOP -> stopTimer(action)
                }.exhaustive
            }
        }
    }

    private suspend fun selectActionNames(actionNames: ActionNames) {
        if (actionNames.group) {
            actionNamesGroupId.value = if (actionNames.id > 0) {
                actionNamesGroupName = actionNames.name
                actionNames.id
            } else {
                actionNamesGroupName = ""
                null
            }
        } else {
            try {
                createAction(actionNames.id)
            } catch (e: Exception) {
                _uiEvent.send(
                    UiEvent.Error(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            }
        }
    }

    private suspend fun changeActionStatus(action: ActionWithInfo) {
        Timber.tag("tag153")
            .d("ActionsViewModel: update id=${action.id} status=${action.status.name}")
        try {
            updateActionStatus(action)
        } catch (e: Exception) {
            _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun stopActionAndTimer(action: ActionWithInfo) {
        Timber.tag("tag153").d("ActionsViewModel: accept stop id=${action.id}")
        try {
            stopAction(action)
            stopTimer(action)
        } catch (e: Exception) {
            _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
