package ru.ksart.timefocus.ui.history.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo

class HistoryDiffCallback : DiffUtil.ItemCallback<ActionIntervalsWithInfo>() {

    override fun areItemsTheSame(
        oldItem: ActionIntervalsWithInfo,
        newItem: ActionIntervalsWithInfo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ActionIntervalsWithInfo,
        newItem: ActionIntervalsWithInfo
    ): Boolean {
        return oldItem == newItem
    }
}
