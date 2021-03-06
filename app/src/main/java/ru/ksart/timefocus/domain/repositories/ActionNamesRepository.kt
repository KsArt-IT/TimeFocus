package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.data.db.models.ActionNames

interface ActionNamesRepository {

    suspend fun getActionsNamesWithoutGroup(): List<ActionNames>
    suspend fun getActionNamesByGroupId(groupId: Long): List<ActionNames>

    fun actionNamesWithoutGroup(): Flow<List<ActionNames>>
    fun actionNamesByGroupId(groupId: Long): Flow<List<ActionNames>>

    suspend fun getActionNamesByName(name: String): ActionNames

    fun actionNames(): Flow<List<ActionNames>>
//    fun actionNamesByGroupId(groupId: Long?) : Flow<List<ActionNames>>

}
