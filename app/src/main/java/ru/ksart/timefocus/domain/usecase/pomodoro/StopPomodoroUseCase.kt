package ru.ksart.timefocus.domain.usecase.pomodoro

import ru.ksart.timefocus.domain.repositories.Pomodoro
import javax.inject.Inject

class StopPomodoroUseCase @Inject constructor(
    private val pomodoro: Pomodoro
) {

    suspend operator fun invoke(params: Long) {
        try {
            pomodoro.stop(params)
        } catch (e: Exception) {
        }
    }

}
