package ru.ksart.timefocus.ui.actions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo

class ActionNamesAdapter(
    private val onClick: (UiAction<ActionWithInfo>) -> Unit
) : ListAdapter<ActionNames, ActionNamesViewHolder>(ActionNamesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionNamesViewHolder {
        return ActionNamesViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: ActionNamesViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
