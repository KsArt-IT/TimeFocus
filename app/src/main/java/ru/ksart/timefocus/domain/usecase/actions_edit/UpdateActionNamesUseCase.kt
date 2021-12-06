package ru.ksart.timefocus.domain.usecase.actions_edit

import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.domain.repositories.ActionNamesEditRepository
import javax.inject.Inject

class UpdateActionNamesUseCase @Inject constructor(
    private val repository: ActionNamesEditRepository,
) {

    suspend operator fun invoke(params: ActionNames) {
        repository.update(params)
    }

}
