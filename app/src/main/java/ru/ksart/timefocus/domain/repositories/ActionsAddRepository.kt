package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.ksart.timefocus.data.db.models.ActionNames

interface ActionsAddRepository {
    fun getActionNames(): Flow<List<ActionNames>>

    suspend fun add(action: ActionNames): Long

    suspend fun update(action: ActionNames)

    suspend fun delete(action: ActionNames)
}
