package ru.ksart.timefocus.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.ActionsDatabase
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo
import ru.ksart.timefocus.di.IoDispatcher
import ru.ksart.timefocus.domain.repositories.IntervalsRepository
import ru.ksart.timefocus.ui.extension.toMidnightToday
import ru.ksart.timefocus.ui.extension.toMidnightTomorrow
import javax.inject.Inject

class IntervalsRepositoryImpl @Inject constructor(
    private val db: ActionsDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : IntervalsRepository {

    override fun getActionsHistoryByDate(date: Instant): Flow<List<ActionIntervalsWithInfo>> {
        val startDate = date.toMidnightToday()
        val endDate = date.toMidnightTomorrow()
        return db.actionIntervalsDao().getIntervalsWithInfoByDate(startDate, endDate)
            .flowOn(dispatcher)
    }
}
