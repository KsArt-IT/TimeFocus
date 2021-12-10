package ru.ksart.timefocus.ui.actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.actions.CreateActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionNamesGroupByGroupIdUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionNamesWithoutGroupUseCase
import ru.ksart.timefocus.domain.usecase.actions.GetActionsWithInfoUseCase
import ru.ksart.timefocus.domain.usecase.actions.StopActionUseCase
import ru.ksart.timefocus.domain.usecase.actions.UpdateActionStatusUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.CounterPomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.SavePomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.StartPomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.StopPomodoroUseCase
import ru.ksart.timefocus.domain.usecase.pomodoro.UsePomodoroUseCase
import ru.ksart.timefocus.domain.usecase.water.SaveWaterUseCase
import ru.ksart.timefocus.domain.usecase.water.WaterVolumeUseCase
import ru.ksart.timefocus.ui.extension.exhaustive
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionsViewModel @Inject constructor(
    private val getActionsWithInfo: GetActionsWithInfoUseCase,

    private val createAction: CreateActionUseCase,
    private val updateActionStatus: UpdateActionStatusUseCase,
    private val stopAction: StopActionUseCase,

    private val getActionsNames: GetActionNamesWithoutGroupUseCase,
    private val getActionsNamesByGroupId: GetActionNamesGroupByGroupIdUseCase,

    private val startPomodoro: StartPomodoroUseCase,
    private val stopPomodoro: StopPomodoroUseCase,
    private val usePomodoro: UsePomodoroUseCase,
    private val counterPomodoro: CounterPomodoroUseCase,
    private val savePomodoro: SavePomodoroUseCase,

    private val waterVolume: WaterVolumeUseCase,
    private val saveWaterUseCase: SaveWaterUseCase,
) : ViewModel() {

    private val actionNamesGroupId = MutableStateFlow<Long?>(null)

    //    private var actionNamesGroupName = ""
    val uiState: StateFlow<UiState<List<ActionNames>>>
    val uiStateAction: StateFlow<UiState<List<ActionWithInfo>>>

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiAction: (UiAction<ActionWithInfo>) -> Unit

    val pomodoroState: StateFlow<UiState<Pair<PomodoroIntervals, Int>>>

    val waterState: StateFlow<UiState<Long>>

    private val _toDay = MutableStateFlow<Instant>(Instant.now())

    init {

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
        // pomodoroState
        val usePomodoroTimer = usePomodoro.observe().mapNotNull { result ->
            when (result) {
                is Results.Success -> {
//                    Timber.tag("tag153").d("ActionsViewModel: Pomodoro observe =${result.data}")
                    // остановка помидора и запись
                    if (result.data.isStarted.not() && result.data.timeDuration == result.data.time) {
                        // сохраняем текущий помидор, только тут
                        savePomodoro()
                        // останавливаем
                        stopPomodoro(result.data.actionNamesId)
                    }
                    result.data
                }
                is Results.Error -> {
                    _uiEvent.send(UiEvent.Error(result.message))
                    null
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = PomodoroIntervals()
            )

        val counterPomodoroTimer = _toDay.flatMapLatest {
            counterPomodoro.observe(it).mapNotNull { result ->
                when (result) {
                    is Results.Success -> result.data
                    is Results.Error -> {
                        _uiEvent.send(UiEvent.Error(result.message))
                        null
                    }
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = 0
            )

        pomodoroState = combine(
            usePomodoroTimer,
            counterPomodoroTimer,
            ::Pair
        ).mapLatest {
            UiState.Success(it)
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState.Loading
            )

        waterState = _toDay.flatMapLatest { date ->
            waterVolume.observe(date)
        }.mapNotNull { result ->
            when (result) {
                is Results.Success -> UiState.Success(result.data)
                is Results.Error -> {
                    _uiEvent.send(UiEvent.Error(result.message))
                    null
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState.Loading
            )
    }

    private suspend fun selectActionNames(actionNames: ActionNames) {
//        Timber.tag("tag153").d("ActionsViewModel: selectActionNames")
        if (actionNames.group) {
            actionNamesGroupId.value = if (actionNames.id > 0) actionNames.id else null
        } else {
            try {
                val isStart = createAction(actionNames.id)
//                Timber.tag("tag153")
//                    .d("ActionsViewModel: Pomodoro start=${actionNames.pomodoro} - $isStart")
                // старт pomodoro
                if (actionNames.pomodoro && isStart) {
                    startPomodoro(actionNames.id to (if (actionNames.pomodoroLong) 50 else 25))
                }
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
//        Timber.tag("tag153")
//            .d("ActionsViewModel: update id=${action.id} status=${action.status.name}")
        try {
            if (action.pomodoro) {
                if (action.status == ActionStatus.ACTIVE) stopPomodoro(action.actionNamesId)
                else if (action.status == ActionStatus.PAUSE) {
                    startPomodoro(action.actionNamesId to (if (action.pomodoroLong) 50 else 25))
                }
            }
            updateActionStatus(action)
        } catch (e: Exception) {
            _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun stopActionAndTimer(action: ActionWithInfo) {
//        Timber.tag("tag153").d("ActionsViewModel: accept stop id=${action.id}")
        try {
            stopPomodoro(action.actionNamesId)
            stopAction(action)
        } catch (e: Exception) {
            _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    fun saveWater() {
        viewModelScope.launch {
//            Timber.tag("tag153").d("ActionsViewModel: saveWater")
            saveWaterUseCase(200)
        }
    }
}
