package ru.ksart.timefocus.domain.repositories

interface SettingsRepository {
    suspend fun checkStartFirst(): Boolean
    suspend fun initData()
}
