package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.PomodoroIntervals

interface Pomodoro {

    val pomodoroTimer: Flow<PomodoroIntervals>

    fun pomodoroCounterByDate(date: Instant): Flow<Int>

    suspend fun savePomodoro()

    suspend fun start(actionNameId: Long, timeDuration: Long)

    suspend fun stop(actionNameId: Long)

}
