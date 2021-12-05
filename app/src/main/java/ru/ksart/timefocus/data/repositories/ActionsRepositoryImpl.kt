package ru.ksart.timefocus.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.domain.repositories.ActionsRepository
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.ActionIntervals
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.data.db.models.Actions
import ru.ksart.timefocus.data.db.models.PomodoroIntervals
import timber.log.Timber
import javax.inject.Inject

class ActionsRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ActionsRepository {

/*
    override val actionNamesAll = db.actionNamesDao().getActionNames()
    override val actionNamesWithoutGroup = db.actionNamesDao().getActionNames()
*/

//    override val actionsWithInfo = db.actionsDao().actionsWithInfo()

    override fun getActionsWithInfo(): Flow<List<ActionWithInfo>> =
        db.actionsDao().actionsWithInfo().flowOn(dispatcher)

    override suspend fun addAction(actionNamesId: Long): Long = withContext(dispatcher) {
        Timber.tag("tag153").d("ActionsRepositoryImpl: addAction")
        db.actionsDao().insert(
            Actions(
                actionNamesId = actionNamesId,
                status = ActionStatus.ACTIVE,
                startDate = Instant.now(),
                comment = null,
            )
        )
    }

    private suspend fun insertIntervals(action: ActionWithInfo) {
        withContext(dispatcher) {
            val stopDate = Instant.now()
            // добавляем запись в интервалы
            db.actionIntervalsDao().insert(
                ActionIntervals(
                    actionsId = action.id,
                    actionNamesId = action.actionNamesId,
                    startDate = action.startDate,
                    stopDate = stopDate,
                )
            )
            // запишем помидор если нужно
            if (action.pomodoro) {
                // TODO необходимо сделать проверку на полный помидор
                db.pomodoroIntervalsDao().insert(
                    PomodoroIntervals(
                        actionsId = action.id,
                        actionNamesId = action.actionNamesId,
                        startDate = action.startDate,
                        stopDate = stopDate,
                    )
                )
            }
        }
    }

    override suspend fun changeActionStatus(action: ActionWithInfo) {
        withContext(dispatcher) {
            Timber.tag("tag153").d("ActionsRepositoryImpl updateActionStatus id=${action.id}")
            // если был активным, переходит в паузу
            if (action.status == ActionStatus.ACTIVE) {
                // обновляем статус
                updateActionStatus(action, ActionStatus.PAUSE)
                // добавим интервалы
                insertIntervals(action)
            } else {
                // стартуем новый таймер
                updateActionStatus(action, ActionStatus.ACTIVE)
            }
        }
    }

    private suspend fun updateActionStatus(action: ActionWithInfo, status: ActionStatus) {
        Timber.tag("tag153")
            .d("ActionsRepositoryImpl: updateActionStatus id=${action.id} status=${status.name}")
        db.actionsDao().update(
            Actions(
                id = action.id,
                actionNamesId = action.actionNamesId,
                status = status,
            )
        )
    }

    override suspend fun stopAction(action: ActionWithInfo) {
        withContext(dispatcher) {
            Timber.tag("tag153").d("ActionsRepositoryImpl: stopActionStatus id=${action.id}")
            // добавим интервалы
            insertIntervals(action)
            // удалить активность
            db.actionsDao().deleteActionById(action.id)
        }
    }

    override suspend fun getActionNamesByGroupId(groupId: Long): List<ActionNames> =
        withContext(dispatcher) {
            db.actionNamesDao().getActionNamesByGroupId(groupId)
        }

    override suspend fun getActionsNamesWithoutGroup(): List<ActionNames> = withContext(dispatcher) {
        db.actionNamesDao().getActionsNamesWithoutGroup()
    }
}
