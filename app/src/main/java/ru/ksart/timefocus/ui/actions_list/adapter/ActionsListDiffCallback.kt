package ru.ksart.timefocus.ui.actions_list.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ksart.timefocus.data.db.models.ActionNames

class ActionsListDiffCallback : DiffUtil.ItemCallback<ActionNames>() {

    override fun areItemsTheSame(oldItem: ActionNames, newItem: ActionNames): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActionNames, newItem: ActionNames): Boolean {
        return oldItem == newItem
    }
}
