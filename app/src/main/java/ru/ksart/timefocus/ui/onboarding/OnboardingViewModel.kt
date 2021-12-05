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
import ru.ksart.timefocus.domain.usecase.onboarding.CheckStartFirstUseCase
import ru.ksart.timefocus.domain.usecase.onboarding.GetOnboardingScreensUseCase
import ru.ksart.timefocus.domain.usecase.onboarding.InitDataUseCase
import ru.ksart.timefocus.domain.entities.OnboardingScreen
import ru.ksart.timefocus.data.entities.UiEvent
import ru.ksart.timefocus.data.entities.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getOnboardingScreens: GetOnboardingScreensUseCase,
    private val checkStartFirst: CheckStartFirstUseCase,
    private val initData: InitDataUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<OnboardingScreen>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiStateSkip =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val uiStateSkip = _uiStateSkip.asStateFlow()

    private val _uiEvent = Channel<UiEvent<Boolean>>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        requestScreens()
    }

    private fun requestScreens() {
        viewModelScope.launch {
            Timber.tag("tag153").d("requestScreens")
            // передаем евент
            try {
                // проверить первый ли запуск
                val first = checkStartFirst()
                // если первый запуск, покажем Onboarding и инициализируем данные
                if (first) {
                    val list = getOnboardingScreens()
                    _uiState.value = UiState.Success(list)
                    // инициализация базы
                    delay(250)
                    initData()
                    _uiStateSkip.value = UiState.Success(true)
                } else _uiEvent.send(UiEvent.Next())
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Error(e.localizedMessage ?: "Primary data initialization error"))
            }
        }
    }
}
