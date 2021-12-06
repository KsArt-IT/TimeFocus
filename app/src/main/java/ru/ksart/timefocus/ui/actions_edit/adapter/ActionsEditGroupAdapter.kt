package ru.ksart.timefocus.ui.actions_edit.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.ksart.timefocus.data.db.models.ActionNames

class ActionsEditGroupAdapter :
    ListAdapter<ActionNames, ActionsEditGroupViewHolder>(ActionsEditGroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsEditGroupViewHolder {
        return ActionsEditGroupViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ActionsEditGroupViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
