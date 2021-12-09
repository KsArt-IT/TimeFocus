package ru.ksart.timefocus.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo
import ru.ksart.timefocus.data.entities.HistoryActions
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.usecase.actions.GetActionHistoryUseCase
import ru.ksart.timefocus.ui.extension.exhaustive
import ru.ksart.timefocus.ui.extension.minusDay
import ru.ksart.timefocus.ui.extension.plusDay
import ru.ksart.timefocus.ui.extension.toDateFormat
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getActionHistory: GetActionHistoryUseCase,
) : ViewModel() {

    val uiState: StateFlow<UiState<HistoryActions>>
    private val historyDate = MutableStateFlow<Instant>(Instant.now())

    private val _uiEvent = Channel<UiEvent<ActionIntervalsWithInfo>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val accept: (UiAction<ActionIntervalsWithInfo>) -> Unit

    init {

        uiState = historyDate.flatMapLatest { date ->
            Timber.tag("tag153").d("HistoryViewModel date=${date.toDateFormat()}")
            getActionHistory.observe(date)
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
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState.Loading
            )

        accept = { uiAction ->
            viewModelScope.launch {
                when (uiAction) {
                    is UiAction.Click -> _uiEvent.send(UiEvent.Success(uiAction.data))
                }
            }
        }
    }

    fun dateBack() {
        historyDate.value = historyDate.value.minusDay()
        Timber.tag("tag153").d("HistoryViewModel date=${historyDate.value.toDateFormat()}")
    }

    fun dateForward() {
        historyDate.value = historyDate.value.plusDay()
        Timber.tag("tag153").d("HistoryViewModel date=${historyDate.value.toDateFormat()}")
    }
}
