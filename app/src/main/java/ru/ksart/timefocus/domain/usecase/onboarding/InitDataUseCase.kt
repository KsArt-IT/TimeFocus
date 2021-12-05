package ru.ksart.timefocus.domain.usecase.onboarding

import ru.ksart.timefocus.domain.repositories.SettingsRepository
import javax.inject.Inject

class InitDataUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke() {
        repository.initData()
    }
}
