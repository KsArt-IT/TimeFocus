package ru.ksart.timefocus.ui.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo

class HistoryAdapter(
    private val onClick: (UiAction<ActionIntervalsWithInfo>) -> Unit,
) : ListAdapter<ActionIntervalsWithInfo, HistoryViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
