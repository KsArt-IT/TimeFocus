package ru.ksart.timefocus.ui.icons_choice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.ksart.timefocus.model.data.IconChoice
import ru.ksart.timefocus.model.data.UiEvent
import ru.ksart.timefocus.model.data.UiState
import ru.ksart.timefocus.repositories.IconsRepository
import ru.ksart.timefocus.repositories.OnboardingRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IconsChoiceViewModel @Inject constructor(
    private val repository: IconsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<IconChoice>>>(UiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            Timber.tag("tag153").d("IconsChoiceViewModel: requestScreens")
            // передаем состояние
            try {
                val list = repository.requestIcons()
                _uiState.value = UiState(data = list)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Error(message = e.localizedMessage))
            } finally {
                _uiEvent.send(UiEvent.Loading(isLoading = false))
                Timber.tag("tag153").d("IconsChoiceViewModel: ok")
            }
        }
    }
}
