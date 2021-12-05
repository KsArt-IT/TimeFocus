package ru.ksart.timefocus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.ksart.timefocus.data.db.models.ActionSuspend
import ru.ksart.timefocus.data.db.models.ActionSuspendContract

@Dao
interface ActionSuspendDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ActionSuspend)

    @Update
    suspend fun update(item: ActionSuspend)

    @Delete
    suspend fun delete(item: ActionSuspend)

    @Query(value = "SELECT * FROM ${ActionSuspendContract.TABLE_NAME} WHERE ${ActionSuspendContract.Columns.ACTION_NAMES_ID} = :actionNamesId")
    suspend fun getActionSuspend(actionNamesId: Long): List<ActionSuspend>
}
