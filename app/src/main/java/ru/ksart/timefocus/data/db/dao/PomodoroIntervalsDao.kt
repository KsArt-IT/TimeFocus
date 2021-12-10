package ru.ksart.timefocus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import ru.ksart.timefocus.data.db.models.PomodoroIntervalsContract

@Dao
interface PomodoroIntervalsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: PomodoroIntervals)

    @Update
    suspend fun update(item: PomodoroIntervals)

    @Delete
    suspend fun delete(item: PomodoroIntervals)

    @Query("SELECT COUNT(A.id) FROM ${PomodoroIntervalsContract.TABLE_NAME} A WHERE (A.start_date >= :startDate AND A.start_date < :stopDate) OR (A.stop_date <= :stopDate AND A.stop_date > :startDate)")
    fun getCountPomodoroByDate(
        startDate: Instant,
        stopDate: Instant
    ): Flow<Int>

/*
    @Query("SELECT * FROM ${PomodoroIntervalsContract.TABLE_NAME} WHERE ${PomodoroIntervalsContract.Columns.START_DATE} = :startDate AND ${PomodoroIntervalsContract.Columns.STOP_DATE} = :stopDate ORDER BY ${ActionIntervalsContract.Columns.START_DATE} ASC")
    suspend fun getPomodoroIntervalsByDate(startDate: Long, stopDate: Long): List<PomodoroIntervals>
*/
}
