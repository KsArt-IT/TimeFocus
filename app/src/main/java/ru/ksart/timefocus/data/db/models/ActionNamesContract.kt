package ru.ksart.timefocus.data.db.models

object ActionNamesContract {
    const val TABLE_NAME = "action_names" // Основная таблица, список всех активностей

    object Columns {
        const val ID = "id"
        const val NAME = "name" // Название активности или группы
        const val DESCRIPTION = "description" // описание

        const val GROUP = "group" // Показатель группы активностей
        const val GROUP_ID = "group_id" // Принадлежность к группе

        const val SUSPEND = "suspend" // При старте активности, будет применена автоматическая пауза для активностей с id таблицы ActionSuspend
        const val SUSPEND_ALL = "suspend_all" // При старте активности, будет применена автоматическая пауза для всех активностей

        const val POMODORO = "pomodoro" // Будет запущен таймер помидора
        const val POMODORO_LONG = "pomodoro_long" // Время помидора долгий интервал или короткий интервал, короткий может автоматически продлиться в длинный
        const val POMODORO_SWITCH_ID = "pomodoro_switch_id" // По завершению помидора сменить активность на отдых

        const val COLOR = "color" // Цвет активности
        const val ICON = "icon" // Иконка активности

        const val NUMBER = "number" // номер по порядку
        const val ARCHIVE = "archive" // признак истории
    }
}
