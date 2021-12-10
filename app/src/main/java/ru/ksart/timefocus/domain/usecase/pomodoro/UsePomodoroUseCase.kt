package ru.ksart.timefocus.domain.usecase.pomodoro

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.Pomodoro
import javax.inject.Inject

class UsePomodoroUseCase @Inject constructor(
    private val pomodoro: Pomodoro
) {

    fun observe(): Flow<Results<PomodoroIntervals>> = pomodoro.pomodoroTimer
        .map { Results.Success(it) }
        .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }

}
