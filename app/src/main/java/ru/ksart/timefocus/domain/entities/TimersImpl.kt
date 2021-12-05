package ru.ksart.timefocus.domain.entities

import android.os.CountDownTimer
import org.threeten.bp.Instant
import ru.ksart.timefocus.domain.repositories.Timers
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.ui.extension.TIMER_INTERVAL
import timber.log.Timber
import javax.inject.Inject

class TimersImpl @Inject constructor() : Timers {

    private val timers: MutableMap<Long, ActionWithInfo> = mutableMapOf()
    private var countDownTimer: CountDownTimer? = null

    override suspend fun start(action: ActionWithInfo): ActionStatus {
        Timber.tag("tag153").d("Timers: ACTIVE id=${action.id}")
        timers[action.id] = action
        if (timers.size == 1) {
            initTimer()
            startTimer()
        }
        return ActionStatus.ACTIVE
    }

    override suspend fun pause(action: ActionWithInfo): ActionStatus {
        Timber.tag("tag153").d("Timers: PAUSE id=${action.id}")
        stop(action)
        return ActionStatus.PAUSE
    }

    override suspend fun stop(action: ActionWithInfo): ActionStatus {
        Timber.tag("tag153").d("Timers: STOP id=${action.id}")
        // проверяем в списке и удаляем
        timers.remove(action.id)
        // если таймеров больше нет, останавливаем
        if (timers.isEmpty()) stopTimer()
        return ActionStatus.STOP
    }

    private fun onTimerTick() {
        val time = Instant.now()
        timers.values.map { action ->
            action.current = time.toEpochMilli() - action.startDate.toEpochMilli()
        }
    }

    private fun initTimer() {
        stopTimer()
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                onTimerTick()
            }

            override fun onFinish() {
//                Timber.tag("tag153").d("Timers: onFinish")
                // TODO перезапустить
            }

        }
    }

    private fun startTimer() {
        Timber.tag("tag153").d("Timers: startTimer")
        countDownTimer?.start()
    }

    private fun stopTimer() {
        Timber.tag("tag153").d("Timers: stopTimer")
        countDownTimer?.cancel()
        countDownTimer = null
    }
}
