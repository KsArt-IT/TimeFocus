package ru.ksart.timefocus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.ActionIntervals
import ru.ksart.timefocus.data.db.models.ActionIntervalsContract
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo
import ru.ksart.timefocus.data.db.models.ActionNamesContract

@Dao
interface ActionIntervalsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ActionIntervals)

    @Update
    suspend fun update(item: ActionIntervals)

    @Delete
    suspend fun delete(item: ActionIntervals)

    @Query("SELECT * FROM ${ActionIntervalsContract.TABLE_NAME} WHERE ${ActionIntervalsContract.Columns.ACTION_ID} = :actionId ORDER BY ${ActionIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getIntervalsByAction(actionId: Long): List<ActionIntervals>

    @Query("SELECT * FROM ${ActionIntervalsContract.TABLE_NAME} WHERE ${ActionIntervalsContract.Columns.START_DATE} = :startDate AND ${ActionIntervalsContract.Columns.STOP_DATE} = :stopDate ORDER BY ${ActionIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getIntervalsByDate(startDate: Long, stopDate: Long): List<ActionIntervals>

    @Query("SELECT A.*, B.name, B.color, B.icon, B.pomodoro, B.pomodoro_long, B.pomodoro_switch_id, B.suspend, B.suspend_all FROM ${ActionIntervalsContract.TABLE_NAME} A LEFT JOIN ${ActionNamesContract.TABLE_NAME} B ON A.action_names_id = B.id WHERE (A.start_date >= :startDate AND A.start_date < :stopDate) OR (A.stop_date <= :stopDate AND A.stop_date > :startDate) ORDER BY A.start_date DESC")
    fun getIntervalsWithInfoByDate(
        startDate: Instant,
        stopDate: Instant
    ): Flow<List<ActionIntervalsWithInfo>>
}
