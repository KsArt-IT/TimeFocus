package ru.ksart.timefocus.ui.actions.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ksart.timefocus.databinding.ItemActionsNameBinding
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset

class ActionNamesViewHolder(
    private val binding: ItemActionsNameBinding,
    private val onClick: (UiAction<ActionWithInfo>) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var item: ActionNames? = null

    init {
        binding.run {
            root.setOnClickListener { item?.let { onClick(UiAction.Select(it)) } }
        }
    }

    fun onBind(item: ActionNames) {
        this.item = item

        binding.run {
            name.text = item.name
            icon.loadSvgFromAsset(item.icon)
            icon.setColorFilter(item.color, PorterDuff.Mode.SRC_IN)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (UiAction<ActionWithInfo>) -> Unit
        ) = ItemActionsNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { ActionNamesViewHolder(it, onClick) }
    }
}
