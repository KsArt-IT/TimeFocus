package ru.ksart.timefocus.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ksart.timefocus.data.db.models.ActionIntervals
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionSuspend
import ru.ksart.timefocus.data.db.models.Actions
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import ru.ksart.timefocus.data.db.models.Water

@Database(
    entities = [
        ActionNames::class,
        ActionIntervals::class,
        Actions::class,
        ActionSuspend::class,
        PomodoroIntervals::class,
        Water::class,
    ],
    version = ActionsDatabaseImpl.DB_VERSION,
    // room.schemaLocation для генерации схем RoomDao
    exportSchema = false,
)
@TypeConverters(DateConverters::class)
abstract class ActionsDatabaseImpl : RoomDatabase(), ActionsDatabase {

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "actions_database.sqlite"
    }
}
