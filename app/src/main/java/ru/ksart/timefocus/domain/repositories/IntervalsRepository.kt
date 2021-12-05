package ru.ksart.timefocus.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo

interface IntervalsRepository {
    fun getActionsHistoryByDate(date: Instant): Flow<List<ActionIntervalsWithInfo>>
}
