package ru.ksart.timefocus.domain.usecase.actions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class GetActionNamesWithoutGroupUseCase @Inject constructor(
    private val repository: ActionNamesRepository
) {

    fun observe(): Flow<Results<List<ActionNames>>> = repository.actionNamesWithoutGroup()
        .map { Results.Success(it) }
        .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }
}
