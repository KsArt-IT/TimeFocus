package ru.ksart.timefocus.repositories

interface SettingsRepository {
    suspend fun checkStartFirst(): Boolean
    suspend fun initData()
}
