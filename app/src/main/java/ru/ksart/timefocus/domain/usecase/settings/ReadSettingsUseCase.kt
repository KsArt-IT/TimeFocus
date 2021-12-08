package ru.ksart.timefocus.domain.usecase.settings

import ru.ksart.timefocus.domain.entities.AppSettings
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.SettingsRepository
import javax.inject.Inject

class ReadSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Results<AppSettings> {
        return try {
            Results.Success(repository.readSettings())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "")
        }
    }
}
