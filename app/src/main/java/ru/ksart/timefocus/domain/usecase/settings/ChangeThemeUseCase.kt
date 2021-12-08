package ru.ksart.timefocus.domain.usecase.settings

import ru.ksart.timefocus.domain.entities.AppSettings
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.SettingsRepository
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(){
        try {
            repository.changeTheme()
        } catch (e: Exception) {
        }
    }
}
