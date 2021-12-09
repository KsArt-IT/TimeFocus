package ru.ksart.timefocus.domain.usecase.actions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.entities.HistoryActions
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.IntervalsRepository
import ru.ksart.timefocus.ui.extension.toDateFormat
import ru.ksart.timefocus.ui.extension.toTimeDisplay
import javax.inject.Inject

class GetActionHistoryUseCase @Inject constructor(
    private val repository: IntervalsRepository
) {

    fun observe(params: Instant): Flow<Results<HistoryActions>> {
        return repository.getActionsHistoryByDate(params)
            .mapLatest { list ->
                val timeSum = list.sumOf {
                    it.stopDate.epochSecond - it.startDate.epochSecond
                }
                Results.Success(
                    HistoryActions(
                        intervals = list,
                        date = params.toDateFormat(),
                        timeSum = timeSum.toTimeDisplay(),
                    )
                )
            }
            .catch { Results.Error(it.localizedMessage ?: "An unexpected error occurred") }
    }

}
