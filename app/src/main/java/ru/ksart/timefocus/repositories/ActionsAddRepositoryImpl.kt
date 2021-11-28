package ru.ksart.timefocus.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.model.db.ActionsDatabase
import ru.ksart.timefocus.model.db.models.ActionNames
import javax.inject.Inject

class ActionsAddRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase
) : ActionsAddRepository {

    override val actionNames = db.actionNamesDao().getActionNames()

    override suspend fun add(action: ActionNames): Long = withContext(Dispatchers.IO) {
        db.actionNamesDao().insert(action)
    }

    override suspend fun update(action: ActionNames) {
        withContext(Dispatchers.IO) {
            db.actionNamesDao().update(action)
        }
    }

    override suspend fun delete(action: ActionNames) {
        withContext(Dispatchers.IO) {
            db.actionNamesDao().delete(action)
        }
    }
}
