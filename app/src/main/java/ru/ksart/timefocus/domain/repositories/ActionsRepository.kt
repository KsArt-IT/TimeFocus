package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo

interface ActionsRepository {

    suspend fun isActiveActionsByActionsNameId(actionNamesId: Long): Boolean
    suspend fun addAction(actionNamesId: Long): Boolean
    suspend fun changeActionStatus(action: ActionWithInfo)
    suspend fun stopAction(action: ActionWithInfo)

    fun getActionsWithInfo(): Flow<List<ActionWithInfo>>

}
