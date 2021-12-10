package ru.ksart.timefocus.data.repositories

import android.content.Context
import android.media.RingtoneManager
import android.os.CountDownTimer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.repositories.Pomodoro
import ru.ksart.timefocus.ui.extension.toMidnightToday
import ru.ksart.timefocus.ui.extension.toMidnightTomorrow
import timber.log.Timber
import javax.inject.Inject

class PomodoroImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : Pomodoro {

    private var countDownTimer: CountDownTimer? = null
    private val _pomodoroTimer = MutableStateFlow(
        PomodoroIntervals(
            actionNamesId = 0,
            time = 0,
            timeDuration = 0,
            isStarted = false
        )
    )
    override val pomodoroTimer: StateFlow<PomodoroIntervals> = _pomodoroTimer.asStateFlow()

    override fun pomodoroCounterByDate(date: Instant): Flow<Int> {
        val startDate = date.toMidnightToday()
        val endDate = date.toMidnightTomorrow()
        return db.pomodoroIntervalsDao().getCountPomodoroByDate(startDate, endDate)
            .flowOn(dispatcher)
    }

    // сохраним интервал помидора, только целые
    override suspend fun savePomodoro() {
        withContext(dispatcher) {
            if (
                pomodoroTimer.value.isStarted.not() &&
                pomodoroTimer.value.timeDuration > 0 &&
                pomodoroTimer.value.timeDuration == pomodoroTimer.value.time
            ) {
                Timber.tag("tag153").d("PomodoroImpl: save")
                db.pomodoroIntervalsDao().insert(pomodoroTimer.value)
                // звук
                RingtoneManager
                    .getRingtone(
                        context,
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    )
                    .play()
            }
        }
    }

    override suspend fun start(actionNameId: Long, timeDuration: Long) {
        // не перезапускать
        Timber.tag("tag153").d("PomodoroImpl: start")
        if (pomodoroTimer.value.isStarted) return

        // старт помидора
        _pomodoroTimer.value = PomodoroIntervals(
            actionNamesId = actionNameId,
            time = 0,
            timeDuration = timeDuration,
            isStarted = true,
        )

        Timber.tag("tag153").d("PomodoroImpl: startTimer")
        countDownTimer = object : CountDownTimer(timeDuration * TIME_MINUTE, TIME_MINUTE) {
            override fun onTick(millisUntilFinished: Long) {
                onTimerTick()
            }

            override fun onFinish() {
                onTimerStop()
            }

        }
        countDownTimer?.start()
    }

    override suspend fun stop(actionNameId: Long) {
        Timber.tag("tag153").d("PomodoroImpl: stopTimer")
        if (pomodoroTimer.value.actionNamesId != actionNameId) return
        countDownTimer?.cancel()
        countDownTimer = null
        _pomodoroTimer.value = pomodoroTimer.value.copy(isStarted = false)
    }

    private fun onTimerTick() {
        Timber.tag("tag153").d("PomodoroImpl: onTimerTick")
        val time = Instant.now()
        _pomodoroTimer.value = pomodoroTimer.value.copy(
            time = (time.toEpochMilli() - pomodoroTimer.value.startDate.toEpochMilli()) / TIME_MINUTE
        )
    }

    private fun onTimerStop() {
        _pomodoroTimer.value = pomodoroTimer.value.copy(
            stopDate = Instant.now(),
            time = pomodoroTimer.value.timeDuration,
            isStarted = false
        )
    }

    private companion object {
//        const val TIME_MINUTE = 1000L
        const val TIME_MINUTE = 60000L
    }
}
