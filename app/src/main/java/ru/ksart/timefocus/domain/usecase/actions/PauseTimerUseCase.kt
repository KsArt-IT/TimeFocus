package ru.ksart.timefocus.domain.usecase.actions

import ru.ksart.timefocus.domain.repositories.Timers
import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import javax.inject.Inject

class PauseTimerUseCase @Inject constructor(
    private val timer: Timers
) {

    suspend operator fun invoke(params: ActionWithInfo): ActionStatus = timer.pause(params)

}
