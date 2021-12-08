package ru.ksart.timefocus.domain.usecase.settings

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.domain.repositories.SettingsRepository
import javax.inject.Inject

class ChangeSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    fun observe(): Flow<Boolean> = repository.changeSettings

}
