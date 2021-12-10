package ru.ksart.timefocus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.Water
import ru.ksart.timefocus.data.db.models.WaterContract

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: Water)

    @Update
    suspend fun update(item: Water)

    @Delete
    suspend fun delete(item: Water)

    @Query("SELECT SUM(A.volume) FROM ${WaterContract.TABLE_NAME} A WHERE (A.date >= :startDate AND A.date < :stopDate)")
    fun getWaterVolumeByDate(
        startDate: Instant,
        stopDate: Instant
    ): Flow<Long>

}
