package ru.ksart.timefocus.domain.usecase.settings

import ru.ksart.timefocus.domain.repositories.SettingsRepository
import javax.inject.Inject

class UnregisterChangeSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke() {
        repository.unregisterChangeSettings()
    }
}
