package ru.ksart.timefocus.ui.actions_list.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ksart.timefocus.databinding.ItemActionsListBinding
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset

class ActionsListViewHolder(
    private val binding: ItemActionsListBinding,
    private val onClick: (ActionNames) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: ActionNames? = null

    init {
        binding.root.setOnClickListener { item?.let { onClick(it) } }
    }

    fun onBind(item: ActionNames) {
        this.item = item

        binding.run {
            actionName.text = item.name
//            description.text = item.description
            iconItem.loadSvgFromAsset(item.icon)
            iconItem.setColorFilter(item.color, PorterDuff.Mode.SRC_IN)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (ActionNames) -> Unit
        ) = ItemActionsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { ActionsListViewHolder(it, onClick) }
    }
}
