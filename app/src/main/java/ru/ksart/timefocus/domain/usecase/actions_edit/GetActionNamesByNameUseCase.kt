package ru.ksart.timefocus.domain.usecase.actions_edit

import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class GetActionNamesByNameUseCase @Inject constructor(
    private val repository: ActionNamesRepository
) {

    suspend operator fun invoke(params: String) = repository.getActionNamesByName(params)

}
