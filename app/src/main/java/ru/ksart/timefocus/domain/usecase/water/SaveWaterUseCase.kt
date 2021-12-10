package ru.ksart.timefocus.domain.usecase.water

import ru.ksart.timefocus.domain.repositories.WaterRepository
import javax.inject.Inject

class SaveWaterUseCase @Inject constructor(
    private val repository: WaterRepository
) {

    suspend operator fun invoke(params: Long) {
        try {
            repository.saveWaterVolume(params)
        } catch (e: Exception) {
        }
    }

}
