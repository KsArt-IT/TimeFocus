package ru.ksart.timefocus.domain.usecase.pomodoro

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.Pomodoro
import javax.inject.Inject

class CounterPomodoroUseCase @Inject constructor(
    private val pomodoro: Pomodoro
) {

    fun observe(params: Instant): Flow<Results<Int>> = pomodoro.pomodoroCounterByDate(params)
        .map { Results.Success(it) }
        .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }

}
