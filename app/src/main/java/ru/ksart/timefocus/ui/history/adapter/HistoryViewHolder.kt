package ru.ksart.timefocus.ui.history.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ksart.timefocus.data.db.models.ActionIntervalsWithInfo
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.databinding.ItemHistoryBinding
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset
import ru.ksart.timefocus.ui.extension.toTimeDisplay
import ru.ksart.timefocus.ui.extension.toTimeFormat

class HistoryViewHolder(
    private val binding: ItemHistoryBinding,
    private val onClick: (UiAction<ActionIntervalsWithInfo>) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var item: ActionIntervalsWithInfo? = null

    init {
        binding.run { root.setOnClickListener { item?.let { onClick(UiAction.Click(it)) } } }
    }

    fun onBind(item: ActionIntervalsWithInfo) {
        this.item = item

        binding.run {
            name.text = item.name
            icon.loadSvgFromAsset(item.icon)
            icon.setColorFilter(item.color, PorterDuff.Mode.SRC_IN)
            times.text = "${item.startDate.toTimeFormat()} - ${item.stopDate.toTimeFormat()}"
            timer.text = (item.stopDate.epochSecond - item.startDate.epochSecond).toTimeDisplay()
//            timer.text = ((item.stopDate.toEpochMilli() - item.startDate.toEpochMilli())/1000).toTimeDisplay()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (UiAction<ActionIntervalsWithInfo>) -> Unit,
        ) = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { HistoryViewHolder(it, onClick) }
    }
}
