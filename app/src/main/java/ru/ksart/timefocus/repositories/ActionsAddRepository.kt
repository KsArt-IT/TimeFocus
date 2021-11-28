package ru.ksart.timefocus.repositories

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.model.db.models.ActionNames

interface ActionsAddRepository {
    val actionNames: Flow<List<ActionNames>>

    suspend fun add(action: ActionNames): Long

    suspend fun update(action: ActionNames)

    suspend fun delete(action: ActionNames)
}
