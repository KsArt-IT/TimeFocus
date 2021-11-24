package ru.ksart.timefocus.model.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.ksart.timefocus.model.db.models.ActionIntervals
import ru.ksart.timefocus.model.db.models.ActionIntervalsContract
import ru.ksart.timefocus.model.db.models.PomodoroIntervals
import ru.ksart.timefocus.model.db.models.PomodoroIntervalsContract

@Dao
interface PomodoroIntervalsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: PomodoroIntervals)

    @Update
    suspend fun update(item: PomodoroIntervals)

    @Delete
    suspend fun delete(item: PomodoroIntervals)

    @Query("SELECT * FROM ${PomodoroIntervalsContract.TABLE_NAME} WHERE ${PomodoroIntervalsContract.Columns.ACTIONS_ID} = :actionId ORDER BY ${PomodoroIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getPomodoroIntervalsByAction(actionId: Long): List<ActionIntervals>

    @Query("SELECT * FROM ${PomodoroIntervalsContract.TABLE_NAME} WHERE ${PomodoroIntervalsContract.Columns.START_DATE} = :startDate AND ${PomodoroIntervalsContract.Columns.STOP_DATE} = :stopDate ORDER BY ${ActionIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getPomodoroIntervalsByDate(startDate: Long, stopDate: Long): List<PomodoroIntervals>
}
