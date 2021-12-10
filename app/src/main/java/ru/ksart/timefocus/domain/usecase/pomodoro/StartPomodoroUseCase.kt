package ru.ksart.timefocus.domain.usecase.pomodoro

import ru.ksart.timefocus.domain.repositories.Pomodoro
import javax.inject.Inject

class StartPomodoroUseCase @Inject constructor(
    private val pomodoro: Pomodoro
) {

    suspend operator fun invoke(params: Pair<Long, Long>) {
        try {
            pomodoro.start(params.first, params.second)
        } catch (e: Exception) {
        }
    }

}
