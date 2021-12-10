package ru.ksart.timefocus.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.data.db.models.ActionIntervalsContract
import ru.ksart.timefocus.data.db.models.ActionNamesContract
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.data.db.models.Actions
import ru.ksart.timefocus.data.db.models.ActionsContract

@Dao
interface ActionsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: Actions)

    @Update
    suspend fun update(item: Actions)

    @Delete
    suspend fun delete(item: Actions)

    @Query("DELETE FROM ${ActionsContract.TABLE_NAME} WHERE ${ActionsContract.Columns.ID} = :id")
    suspend fun deleteActionById(id: Long)

    @Query("SELECT * FROM ${ActionsContract.TABLE_NAME}  WHERE ${ActionsContract.Columns.ACTION_NAMES_ID} = :id LIMIT 1")
    fun select(id: Long): Actions?

/*
    @Query("UPDATE ${ActionsContract.TABLE_NAME} SET ${ActionsContract.Columns.STATUS} = :status WHERE ${ActionsContract.Columns.ID} = :id")
    suspend fun updateActionStatusById(id: Long, status: ActionStatus)

    @Query("SELECT * FROM ${ActionsContract.TABLE_NAME} WHERE ${ActionsContract.Columns.STATUS} = :status ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
    suspend fun getActionsByStatus(status: ActionStatus = ActionStatus.ACTIVE): List<Actions>
*/

/*
    @Query("SELECT * FROM ${ActionsContract.TABLE_NAME} ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
    fun getActions(): Flow<List<Actions>>
*/

    //    @Query("SELECT A.*, B.name, B.color, B.icon, B.pomodoro, B.pomodoro_long, B.pomodoro_switch_id, B.suspend, B.suspend_all, C.times_action FROM ${ActionsContract.TABLE_NAME} A LEFT JOIN ${ActionNamesContract.TABLE_NAME} B ON A.action_names_id = B.id LEFT JOIN (SELECT D.actions_id, SUM(D.stop_date - D.start_date) AS ${ActionsContract.Columns.TIMES_ACTION} FROM ${ActionIntervalsContract.TABLE_NAME} D GROUP BY D.actions_id) C ON A.id = C.actions_id ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
//    @Query("SELECT A.* FROM ${ActionsContract.TABLE_NAME} A INNER JOIN (SELECT D.route_id FROM (SELECT E.route_id FROM ${BusRouteStopsContract.TABLE_NAME} E WHERE ${BusRouteStopsContract.Columns.STOP_ID} = :stopIdTo) D INNER JOIN (SELECT F.route_id FROM ${BusRouteStopsContract.TABLE_NAME} F WHERE ${BusRouteStopsContract.Columns.STOP_ID} = :stopIdFrom) C ON D.route_id = C.route_id) B ON A.route_id = B.route_id ORDER BY ${BusRouteNamesContract.Columns.NAME} ASC")
//    @Query("SELECT A.*, B.name, B.color, B.icon, B.pomodoro, B.pomodoro_long, B.pomodoro_switch_id, B.action_mode, B.suspend, B.suspend_all FROM ${ActionsContract.TABLE_NAME} A LEFT JOIN ${ActionNamesContract.TABLE_NAME} B ON A.action_names_id = B.id ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
//    suspend fun getActionsWithInfo(): List<ActionWithInfo>

    //    @Query("SELECT A.*, B.name, B.color, B.icon, B.pomodoro, B.pomodoro_long, B.pomodoro_switch_id, B.suspend, B.suspend_all FROM ${ActionsContract.TABLE_NAME} A LEFT JOIN ${ActionNamesContract.TABLE_NAME} B ON A.action_names_id = B.id ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
    @Query("SELECT A.*, B.name, B.color, B.icon, B.pomodoro, B.pomodoro_long, B.pomodoro_switch_id, B.action_mode, B.suspend, B.suspend_all, C.times_action FROM ${ActionsContract.TABLE_NAME} A LEFT JOIN ${ActionNamesContract.TABLE_NAME} B ON A.action_names_id = B.id  LEFT JOIN (SELECT D.actions_id, SUM(D.stop_date - D.start_date) AS ${ActionsContract.Columns.TIMES_ACTION} FROM ${ActionIntervalsContract.TABLE_NAME} D GROUP BY D.actions_id) C ON A.id = C.actions_id ORDER BY ${ActionsContract.Columns.START_DATE} ASC")
    fun actionsWithInfo(): Flow<List<ActionWithInfo>>
}
