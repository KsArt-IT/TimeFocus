package ru.ksart.timefocus.data.db.models

object ActionIntervalsContract {
    const val TABLE_NAME = "action_intervals" // список интервалов в активности

    object Columns {
        const val ID = "id"
        const val ACTION_ID = "actions_id" // id активности к которому оно принадлежит
        const val ACTION_NAMES_ID = "action_names_id" //
        const val START_DATE = "start_date" // Начало активности
        const val STOP_DATE = "stop_date" // Конец активности
    }
}
