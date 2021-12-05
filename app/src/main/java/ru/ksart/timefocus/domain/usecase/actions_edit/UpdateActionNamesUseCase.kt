package ru.ksart.timefocus.domain.usecase.actions_edit

import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.domain.repositories.ActionsAddRepository
import javax.inject.Inject

class UpdateActionNamesUseCase @Inject constructor(
    private val repository: ActionsAddRepository,
) {

    suspend operator fun invoke(param: ActionNames) {
        repository.update(param)
    }

}
