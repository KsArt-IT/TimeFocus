package ru.ksart.timefocus.ui.icons_choice.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.ksart.timefocus.model.data.IconChoice
import timber.log.Timber

class IconsChoiceDiffCallback : DiffUtil.ItemCallback<IconChoice>() {

    override fun areItemsTheSame(oldItem: IconChoice, newItem: IconChoice): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IconChoice, newItem: IconChoice): Boolean {
        return oldItem == newItem
    }
}
