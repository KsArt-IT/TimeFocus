package ru.ksart.timefocus.model.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.model.db.models.ActionIntervals
import ru.ksart.timefocus.model.db.models.ActionIntervalsContract
import ru.ksart.timefocus.model.db.models.ActionNames
import ru.ksart.timefocus.model.db.models.ActionNamesContract

@Dao
interface ActionNamesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ActionNames): Long

    @Update
    suspend fun update(item: ActionNames)

    @Delete
    suspend fun delete(item: ActionNames)

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} IS NULL AND ${ActionNamesContract.Columns.ARCHIVE} = 0 ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
    fun getActionNames(): Flow<List<ActionNames>>

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.ARCHIVE} > 0 ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
    suspend fun getActionNamesArchive(): List<ActionNames>
}
