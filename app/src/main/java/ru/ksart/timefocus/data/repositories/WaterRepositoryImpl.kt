package ru.ksart.timefocus.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.Water
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.repositories.WaterRepository
import ru.ksart.timefocus.ui.extension.toMidnightToday
import ru.ksart.timefocus.ui.extension.toMidnightTomorrow
import javax.inject.Inject

class WaterRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : WaterRepository {

    override fun getWaterVolumeByDate(date: Instant): Flow<Long> {
        val startDate = date.toMidnightToday()
        val endDate = date.toMidnightTomorrow()
        return db.waterDao().getWaterVolumeByDate(startDate, endDate)
            .flowOn(dispatcher)
    }

    override suspend fun saveWaterVolume(volume: Long) {
        withContext(dispatcher) {
            db.waterDao().insert(Water(volume = volume))
        }
    }

}
