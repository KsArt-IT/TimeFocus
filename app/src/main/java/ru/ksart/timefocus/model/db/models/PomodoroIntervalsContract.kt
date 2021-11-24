package ru.ksart.timefocus.model.db.models

object PomodoroIntervalsContract {
    const val TABLE_NAME = "pomodoro_intervals" // список интервалов в активности в помидорах

    object Columns {
        const val ID = "id"
        const val ACTIONS_ID = "actions_id" // id активности к которому оно принадлежит
        const val START_DATE = "start_date" // Начало активности
        const val STOP_DATE = "stop_date" // Конец активности
    }
}
