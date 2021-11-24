package ru.ksart.timefocus.model.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.ksart.timefocus.model.data.ActionStatus
import ru.ksart.timefocus.model.db.models.Actions
import ru.ksart.timefocus.model.db.models.ActionsContract

@Dao
interface ActionsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: Actions)

    @Update
    suspend fun update(item: Actions)

    @Delete
    suspend fun delete(item: Actions)

    @Query(value = "SELECT * FROM ${ActionsContract.TABLE_NAME} WHERE ${ActionsContract.Columns.STATUS} = :status ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
    suspend fun getActionsByStatus(status: ActionStatus = ActionStatus.Active): List<Actions>
}
