package ru.ksart.timefocus.domain.usecase.actions

import ru.ksart.timefocus.domain.repositories.ActionsRepository
import javax.inject.Inject

class CreateActionUseCase @Inject constructor(
    private val repository: ActionsRepository,
) {

    suspend operator fun invoke(params: Long): Boolean {
        return try {
            // проверить не запущена ли активность
            if (repository.isActiveActionsByActionsNameId(params).not()) {
                repository.addAction(params)
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }

}
