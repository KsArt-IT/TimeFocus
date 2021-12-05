package ru.ksart.timefocus.data.entities

import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo

data class HistoryActions(
    val intervals: List<ActionIntervalsWithInfo>,
    val date: String,
    val timeSum: String,
)
