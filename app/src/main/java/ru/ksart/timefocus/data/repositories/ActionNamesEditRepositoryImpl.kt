package ru.ksart.timefocus.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.repositories.ActionNamesEditRepository
import javax.inject.Inject

class ActionNamesEditRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ActionNamesEditRepository {

    override suspend fun add(action: ActionNames) {
        withContext(dispatcher) {
            db.actionNamesDao().insert(action)
        }
    }

    override suspend fun update(action: ActionNames) {
        withContext(dispatcher) {
            db.actionNamesDao().update(action)
        }
    }

    override suspend fun delete(action: ActionNames) {
        withContext(dispatcher) {
            db.actionNamesDao().delete(action)
        }
    }

    override suspend fun removeGroupIdFromActionNames(groupId: Long) {
        withContext(dispatcher) {
            db.actionNamesDao().removeGroupIdFromActionNames(groupId)
        }
    }
}
