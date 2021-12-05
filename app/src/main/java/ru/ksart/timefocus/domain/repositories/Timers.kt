package ru.ksart.timefocus.domain.repositories

import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.data.db.models.ActionWithInfo

interface Timers {

    suspend fun start(action: ActionWithInfo): ActionStatus

    suspend fun pause(action: ActionWithInfo): ActionStatus

    suspend fun stop(action: ActionWithInfo): ActionStatus

}
