package ru.ksart.timefocus.ui.actions_list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.ksart.timefocus.data.db.models.ActionNames

class ActionsListAdapter(
    private val onClick: (ActionNames) -> Unit
) : ListAdapter<ActionNames, ActionsListViewHolder>(ActionsListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsListViewHolder {
        return ActionsListViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: ActionsListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
