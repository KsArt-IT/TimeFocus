package ru.ksart.timefocus.ui.actions.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ksart.timefocus.data.db.models.ActionWithInfo

class ActionsDiffCallback : DiffUtil.ItemCallback<ActionWithInfo>() {

    override fun areItemsTheSame(oldItem: ActionWithInfo, newItem: ActionWithInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActionWithInfo, newItem: ActionWithInfo): Boolean {
        return oldItem == newItem
    }
}
