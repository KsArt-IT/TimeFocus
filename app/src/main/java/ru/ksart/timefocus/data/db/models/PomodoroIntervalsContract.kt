package ru.ksart.timefocus.data.db.models

object PomodoroIntervalsContract {
    const val TABLE_NAME = "pomodoro_intervals" // список интервалов в активности в помидорах

    object Columns {
        const val ID = "id"
        const val ACTION_NAMES_ID = "action_names_id" //
        const val START_DATE = "start_date" // Начало активности
        const val STOP_DATE = "stop_date" // Конец активности

        const val TIME = "time" // Конец активности
        const val TIME_DURATION = "time_duration" // Конец активности
        const val STARTED = "started" // Конец активности
    }
}
