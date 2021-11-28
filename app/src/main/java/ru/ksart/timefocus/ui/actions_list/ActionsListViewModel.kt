package ru.ksart.timefocus.ui.actions_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.ksart.timefocus.model.data.UiEvent
import ru.ksart.timefocus.model.data.UiState
import ru.ksart.timefocus.model.db.models.ActionNames
import ru.ksart.timefocus.repositories.ActionsAddRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActionsListViewModel @Inject constructor(
    private val repository: ActionsAddRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ActionNames>>>(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadList()
    }

    private fun loadList() {
        viewModelScope.launch {
            repository.actionNames.collectLatest {
                Timber.tag("tag153").d("ActionsListViewModel list=${it.size}")
                _uiState.value = UiState(data = it)
            }
        }
    }

}
