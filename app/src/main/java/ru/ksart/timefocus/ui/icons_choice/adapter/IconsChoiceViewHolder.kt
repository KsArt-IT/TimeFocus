package ru.ksart.timefocus.ui.icons_choice.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ksart.timefocus.databinding.ItemIconsChoiceBinding
import ru.ksart.timefocus.domain.entities.IconChoice
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset

class IconsChoiceViewHolder(
    private val binding: ItemIconsChoiceBinding,
    private val onClick: (IconChoice) -> Unit,
    private val isDarkTheme: Boolean,
) : RecyclerView.ViewHolder(binding.root) {

    private var item: IconChoice? = null

    init {
        binding.run { root.setOnClickListener { item?.let { onClick(it) } } }
    }

    fun onBind(item: IconChoice) {
        this.item = item

        binding.run {
            iconItemChoice.loadSvgFromAsset(item.icon)
            iconItemChoice.setColorFilter(
                if (isDarkTheme) Color.WHITE else Color.BLACK,
                PorterDuff.Mode.SRC_IN
            )
//            val file = File("file:///android_asset/icons/${item.icon}").takeIf { it.exists() }
//            val file = "file:///android_asset/icons/${item.icon}".toUri()
//Timber.tag("tag153").d("id=${item.id} = $file")
//            val bm = binding.root.context.assetsBitmap("icons/${item.icon}")
//            Timber.tag("tag153").d("id=${item.id} = $bm")
//            icon.setImageBitmap(bm)
//            icon.load("file:///android_asset/icons/${item.icon}")
//            icon.load(file)
//            file?.let { icon.load(it.absolutePath) }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (IconChoice) -> Unit,
            isDarkTheme: Boolean
        ) = ItemIconsChoiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { IconsChoiceViewHolder(it, onClick, isDarkTheme) }
    }
}
