package ru.ksart.timefocus.domain.usecase.actions_edit

import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class GetActionNamesWithoutGroupUseCase @Inject constructor(
    private val repository: ActionNamesRepository
) {

    suspend operator fun invoke(): Results<List<ActionNames>> {
        return try {
            Results.Success(repository.getActionsNamesWithoutGroup())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

}
