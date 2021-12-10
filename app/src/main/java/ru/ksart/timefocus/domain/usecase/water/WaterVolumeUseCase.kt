package ru.ksart.timefocus.domain.usecase.water

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.WaterRepository
import javax.inject.Inject

class WaterVolumeUseCase @Inject constructor(
    private val water: WaterRepository
) {

    fun observe(params: Instant): Flow<Results<Long>> = water.getWaterVolumeByDate(params)
        .map { Results.Success(it) }
        .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }

}
