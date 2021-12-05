package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo

interface ActionsRepository {
//    val actionNamesAll: Flow<List<ActionNames>>
//    val actionNamesWithoutGroup: Flow<List<ActionNames>>
//    val actionsWithInfo: Flow<List<ActionWithInfo>>

//    fun getActionsNamesWithGroup() : Flow<List<ActionNames>>
    suspend fun getActionsNamesWithoutGroup() : List<ActionNames>
    suspend fun getActionNamesByGroupId(groupId: Long) : List<ActionNames>

//    fun actionNamesById(groupId: Long?) : Flow<List<ActionNames>>

    suspend fun addAction(actionNamesId: Long) : Long
    suspend fun changeActionStatus(action: ActionWithInfo)
    suspend fun stopAction(action: ActionWithInfo)
    fun getActionsWithInfo() : Flow<List<ActionWithInfo>>
}
