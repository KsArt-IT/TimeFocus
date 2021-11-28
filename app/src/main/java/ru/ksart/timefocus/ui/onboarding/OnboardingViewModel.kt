package ru.ksart.timefocus.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.ksart.timefocus.model.data.OnboardingScreen
import ru.ksart.timefocus.model.data.UiEvent
import ru.ksart.timefocus.model.data.UiState
import ru.ksart.timefocus.repositories.OnboardingRepository
import ru.ksart.timefocus.repositories.SettingsRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val settings: SettingsRepository,
    private val repository: OnboardingRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<OnboardingScreen>>>(UiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Unit>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        requestScreens()
    }

    private fun requestScreens() {
        viewModelScope.launch {
            Timber.tag("tag153").d("requestScreens")
            // передаем евент
            _uiEvent.send(
                try {
                    val first = settings.checkStartFirst()
                    // если первый запуск, покажем Onboarding и инициализируем данные
//                if (true) {
                    if (first) {
                        val list = repository.requestScreens()
                        _uiState.value = UiState(data = list)
                        // инициализация базы
                        delay(250)
                        settings.initData()
//                    delay(10000)
                        UiEvent.Loading(isLoading = false)
                    } else UiEvent.Next()
                } catch (e: Exception) {
                    UiEvent.Error(message = e.localizedMessage)
                }
            )
        }
    }
}
