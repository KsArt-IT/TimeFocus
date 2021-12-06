package ru.ksart.timefocus.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class ActionNamesRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ActionNamesRepository {

    override fun getActionNames() = db.actionNamesDao().getActionNames()

    override fun actionNamesByGroupId(groupId: Long?): Flow<List<ActionNames>> {
        return if (groupId == null) callbackFlow { emptyList<ActionNames>() }
        else db.actionNamesDao().actionNamesByGroupId(groupId)
            .flowOn(dispatcher)
    }

    override suspend fun getActionNamesByGroupId(groupId: Long): List<ActionNames> {
        return withContext(dispatcher) {
            db.actionNamesDao().getActionNamesByGroupId(groupId)
        }
    }

    override suspend fun getActionNamesByName(name: String) : ActionNames {
        return withContext(dispatcher) {
            db.actionNamesDao().getActionNamesByName(name)
        }
    }

    override suspend fun getActionsNamesWithoutGroup(): List<ActionNames> {
        return withContext(dispatcher) {
            db.actionNamesDao().getActionsNamesWithoutGroup()
        }
    }
}
