package ru.ksart.timefocus.domain.usecase.actions_edit

import ru.ksart.timefocus.domain.repositories.ActionNamesEditRepository
import javax.inject.Inject

class RemoveGroupIdFromActionNamesByGroupIdUseCase @Inject constructor(
    private val repository: ActionNamesEditRepository,
) {

    suspend operator fun invoke(params: Long) {
        repository.removeGroupIdFromActionNames(params)
    }

}
