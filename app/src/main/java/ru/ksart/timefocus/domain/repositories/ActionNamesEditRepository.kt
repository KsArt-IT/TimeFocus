package ru.ksart.timefocus.domain.repositories

import ru.ksart.timefocus.data.db.models.ActionNames

interface ActionNamesEditRepository {

    suspend fun add(action: ActionNames)

    suspend fun update(action: ActionNames)

    suspend fun delete(action: ActionNames)

    suspend fun removeGroupIdFromActionNames(groupId: Long)
}
