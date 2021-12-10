package ru.ksart.timefocus.data.db

import ru.ksart.timefocus.data.db.dao.ActionIntervalsDao
import ru.ksart.timefocus.data.db.dao.ActionNamesDao
import ru.ksart.timefocus.data.db.dao.ActionSuspendDao
import ru.ksart.timefocus.data.db.dao.ActionsDao
import ru.ksart.timefocus.data.db.dao.PomodoroIntervalsDao
import ru.ksart.timefocus.data.db.dao.WaterDao

interface ActionsDatabase {
    fun actionIntervalsDao(): ActionIntervalsDao
    fun actionNamesDao(): ActionNamesDao
    fun actionsDao(): ActionsDao
    fun actionSuspendDao(): ActionSuspendDao
    fun pomodoroIntervalsDao(): PomodoroIntervalsDao
    fun waterDao(): WaterDao
}
