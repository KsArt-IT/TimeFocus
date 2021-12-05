package ru.ksart.timefocus.ui.actions.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ksart.timefocus.data.db.models.ActionNames

class ActionNamesDiffCallback : DiffUtil.ItemCallback<ActionNames>() {

    override fun areItemsTheSame(oldItem: ActionNames, newItem: ActionNames): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActionNames, newItem: ActionNames): Boolean {
        return oldItem == newItem
    }
}
