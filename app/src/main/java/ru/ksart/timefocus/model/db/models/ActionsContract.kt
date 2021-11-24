package ru.ksart.timefocus.model.db.models

object ActionsContract {
    const val TABLE_NAME = "actions" // Действия

    object Columns {
        const val ID = "id"
        const val ACTION_NAMES_ID = "action_names_id" // название
        const val STATUS = "status_id" // Статус: активный, пауза, остановлено
        const val COMMENT = "comment" // Комментарий
        const val START_DATE = "start_date" // Начало активности
    }
}
