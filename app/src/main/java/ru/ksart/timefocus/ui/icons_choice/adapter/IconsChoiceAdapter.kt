package ru.ksart.timefocus.ui.icons_choice.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.ksart.timefocus.domain.entities.IconChoice

class IconsChoiceAdapter(
    private val onClick: (IconChoice) -> Unit,
) : ListAdapter<IconChoice, IconsChoiceViewHolder>(IconsChoiceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsChoiceViewHolder {
        return IconsChoiceViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: IconsChoiceViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
