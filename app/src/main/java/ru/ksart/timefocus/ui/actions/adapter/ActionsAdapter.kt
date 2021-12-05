package ru.ksart.timefocus.ui.actions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.db.models.ActionWithInfo

class ActionsAdapter(
    private val onClick: (UiAction<ActionWithInfo>) -> Unit
) : ListAdapter<ActionWithInfo, ActionsViewHolder>(ActionsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsViewHolder {
        return ActionsViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: ActionsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
