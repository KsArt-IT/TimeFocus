package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.domain.entities.AppSettings

interface SettingsRepository {
    fun changeTheme()
    suspend fun checkStartFirst(): Boolean
    suspend fun initData()

    val changeSettings: Flow<Boolean>
    suspend fun registerChangeSettings()
    suspend fun unregisterChangeSettings()
    suspend fun readSettings(): AppSettings
}
