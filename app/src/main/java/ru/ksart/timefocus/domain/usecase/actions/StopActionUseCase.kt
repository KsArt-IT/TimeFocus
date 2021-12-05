package ru.ksart.timefocus.domain.usecase.actions

import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.domain.repositories.ActionsRepository
import javax.inject.Inject

class StopActionUseCase @Inject constructor(
    private val repository: ActionsRepository
) {

    suspend operator fun invoke(params: ActionWithInfo) {
        repository.stopAction(params)
    }
}
