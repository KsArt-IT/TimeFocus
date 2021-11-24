package ru.ksart.timefocus.model.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.ksart.timefocus.model.db.models.ActionIntervals
import ru.ksart.timefocus.model.db.models.ActionIntervalsContract

@Dao
interface ActionIntervalsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ActionIntervals)

    @Update
    suspend fun update(item: ActionIntervals)

    @Delete
    suspend fun delete(item: ActionIntervals)

    @Query("SELECT * FROM ${ActionIntervalsContract.TABLE_NAME} WHERE ${ActionIntervalsContract.Columns.ACTIONS_ID} = :actionId ORDER BY ${ActionIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getIntervalsByAction(actionId: Long): List<ActionIntervals>

    @Query("SELECT * FROM ${ActionIntervalsContract.TABLE_NAME} WHERE ${ActionIntervalsContract.Columns.START_DATE} = :startDate AND ${ActionIntervalsContract.Columns.STOP_DATE} = :stopDate ORDER BY ${ActionIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getIntervalsByDate(startDate: Long, stopDate: Long): List<ActionIntervals>
}
