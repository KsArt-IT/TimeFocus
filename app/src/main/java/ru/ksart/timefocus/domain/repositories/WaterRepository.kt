package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant

interface WaterRepository {
    fun getWaterVolumeByDate(date: Instant): Flow<Long>
    suspend fun saveWaterVolume(volume: Long)
}
