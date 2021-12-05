package ru.ksart.timefocus.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.repositories.ActionsAddRepository
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.ActionNames
import javax.inject.Inject

class ActionsAddRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ActionsAddRepository {

    override fun getActionNames() = db.actionNamesDao().getActionNames()

    override suspend fun add(action: ActionNames): Long = withContext(dispatcher) {
        db.actionNamesDao().insert(action)
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
}
