package ru.ksart.timefocus.domain.usecase.actions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.domain.repositories.ActionsRepository
import javax.inject.Inject

class GetActionsWithInfoUseCase @Inject constructor(
    private val repository: ActionsRepository
) {

    fun observe(): Flow<Results<List<ActionWithInfo>>> = repository.getActionsWithInfo()
        .map { Results.Success(it) }
        .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }
}
