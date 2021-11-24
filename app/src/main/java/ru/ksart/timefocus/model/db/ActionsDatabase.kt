package ru.ksart.timefocus.model.db

import ru.ksart.timefocus.model.db.dao.ActionIntervalsDao
import ru.ksart.timefocus.model.db.dao.ActionNamesDao
import ru.ksart.timefocus.model.db.dao.ActionSuspendDao
import ru.ksart.timefocus.model.db.dao.ActionsDao
import ru.ksart.timefocus.model.db.dao.PomodoroIntervalsDao

interface ActionsDatabase {
    fun actionIntervalsDao(): ActionIntervalsDao
    fun actionNamesDao(): ActionNamesDao
    fun actionsDao(): ActionsDao
    fun actionSuspendDao(): ActionSuspendDao
    fun pomodoroIntervalsDao(): PomodoroIntervalsDao
}
