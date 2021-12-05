package ru.ksart.timefocus.ui.icons_choice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.ksart.timefocus.domain.usecase.icons.GetIconsUseCase
import ru.ksart.timefocus.domain.entities.IconChoice
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IconsChoiceViewModel @Inject constructor(
    private val getIcons: GetIconsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<IconChoice>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            Timber.tag("tag153").d("IconsChoiceViewModel: requestScreens")
            // передаем состояние
            try {
                _uiState.value = UiState.Success(getIcons())
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}
