package ru.ksart.timefocus.ui.actions_edit.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.databinding.ItemActionsListBinding
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset

class ActionsEditGroupViewHolder(
    private val binding: ItemActionsListBinding,
) : RecyclerView.ViewHolder(binding.root) {

    var item: ActionNames? = null
        private set

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
        ) = ItemActionsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::ActionsEditGroupViewHolder)
    }
}
