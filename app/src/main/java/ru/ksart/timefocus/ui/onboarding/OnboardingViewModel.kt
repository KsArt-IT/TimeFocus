package ru.ksart.timefocus.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ksart.timefocus.model.data.OnboardingScreen
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

    private val _uiState = MutableStateFlow<UiState<List<OnboardingScreen>>>(UiState.Loading())
    val uiState = _uiState.asStateFlow()

    init {
        requestScreens()
    }

    private fun requestScreens() {
        viewModelScope.launch {
            Timber.tag("tag153").d("requestScreens")
            // передаем состояние
            _uiState.value = try {
                val first = settings.checkStartFirst()
                // если первый запуск, покажем Onboarding и инициализируем данные
//                if (true) {
                if (first) {
                    val list = repository.requestScreens()
                    _uiState.value = UiState.Success(list)
                    // инициализация базы
                    delay(250)
                    settings.initData()
//                    delay(10000)
                    UiState.Loading(isLoading = false)
                } else UiState.Next()
            } catch (e: Exception) {
                UiState.Error(message = e.localizedMessage)
            }
        }
    }
}
