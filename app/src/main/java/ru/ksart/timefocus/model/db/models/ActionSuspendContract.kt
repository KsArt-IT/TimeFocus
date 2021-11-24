package ru.ksart.timefocus.model.db.models

object ActionSuspendContract {
    const val TABLE_NAME = "action_suspend" // активности которые необходимо приостановить

    object Columns {
        const val ID = "id"
        const val ACTION_NAMES_ID = "action_names_id" // какая активность
        const val ACTION_NAMES_SUSPENDED_ID = "action_names_suspend_id" // какие активности приостановит
    }
}
