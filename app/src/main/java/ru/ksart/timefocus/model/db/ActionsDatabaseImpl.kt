package ru.ksart.timefocus.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ksart.timefocus.model.db.models.ActionIntervals
import ru.ksart.timefocus.model.db.models.ActionNames
import ru.ksart.timefocus.model.db.models.ActionSuspend
import ru.ksart.timefocus.model.db.models.Actions
import ru.ksart.timefocus.model.db.models.PomodoroIntervals

@Database(
    entities = [
        ActionNames::class,
        ActionIntervals::class,
        Actions::class,
        ActionSuspend::class,
        PomodoroIntervals::class,
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
