package ru.ksart.timefocus.domain.usecase.actions_edit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class GetActionNamesByGroupIdUseCase @Inject constructor(
    private val repository: ActionNamesRepository
) {

    suspend operator fun invoke(params: Long): Results<List<ActionNames>> {
        return try {
            Results.Success(repository.getActionNamesByGroupId(params))
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "An unexpected error occurred")
        }

    }

    fun observe(params: Long): Flow<Results<List<ActionNames>>> {
        return repository.actionNamesByGroupId(params)
            .mapLatest { Results.Success(it) }
            .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }
    }

}
