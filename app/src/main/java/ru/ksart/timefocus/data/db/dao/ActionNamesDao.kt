package ru.ksart.timefocus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionNamesContract

@Dao
interface ActionNamesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ActionNames): Long

    @Update
    suspend fun update(item: ActionNames)

    @Delete
    suspend fun delete(item: ActionNames)

    @Query("UPDATE ${ActionNamesContract.TABLE_NAME} SET ${ActionNamesContract.Columns.GROUP_ID} = NULL WHERE ${ActionNamesContract.Columns.GROUP_ID} = :groupId")
    suspend fun removeGroupIdFromActionNames(groupId: Long)

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} IS NULL AND ${ActionNamesContract.Columns.ARCHIVE} = 0 ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
    fun getActionNames(): Flow<List<ActionNames>>

//    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} = :groupId ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
//    fun getFlowActionsGroupById(groupId: Long): Flow<List<ActionNames>>

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} = :groupId ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
    fun actionNamesByGroupId(groupId: Long): Flow<List<ActionNames>>

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} = :groupId ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
    suspend fun getActionNamesByGroupId(groupId: Long): List<ActionNames>

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.NAME} = :name LIMIT 1")
    suspend fun getActionNamesByName(name: String): ActionNames

//    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} > 0 ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
//    fun getActionsNamesWithGroup(): Flow<List<ActionNames>>

    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.GROUP_ID} IS NULL ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
    suspend fun getActionsNamesWithoutGroup(): List<ActionNames>

//    @Query("SELECT * FROM ${ActionNamesContract.TABLE_NAME} WHERE ${ActionNamesContract.Columns.ARCHIVE} > 0 ORDER BY ${ActionNamesContract.Columns.NUMBER} ASC")
//    suspend fun getActionNamesArchive(): List<ActionNames>
}
