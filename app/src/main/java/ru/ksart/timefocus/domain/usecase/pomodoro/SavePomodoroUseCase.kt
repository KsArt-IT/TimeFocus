package ru.ksart.timefocus.domain.usecase.pomodoro

import ru.ksart.timefocus.domain.repositories.Pomodoro
import javax.inject.Inject

class SavePomodoroUseCase @Inject constructor(
    private val pomodoro: Pomodoro
) {

    suspend operator fun invoke() {
        try {
            pomodoro.savePomodoro()
        } catch (e: Exception) {
        }
    }

}
