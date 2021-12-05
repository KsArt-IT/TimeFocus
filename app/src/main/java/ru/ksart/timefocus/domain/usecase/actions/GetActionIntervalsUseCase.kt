package ru.ksart.timefocus.domain.usecase.actions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.IntervalsRepository
import javax.inject.Inject

class GetActionIntervalsUseCase @Inject constructor(
    private val repository: IntervalsRepository
) {

    fun observe(params: Instant): Flow<Results<List<ActionIntervalsWithInfo>>> {
        return repository.getActionsHistoryByDate(params)
            .mapLatest { Results.Success(it) }
            .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }
    }

}
