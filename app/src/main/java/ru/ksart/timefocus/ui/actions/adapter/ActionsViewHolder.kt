package ru.ksart.timefocus.ui.actions.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.ksart.timefocus.R
import ru.ksart.timefocus.databinding.ItemActionsBinding
import ru.ksart.timefocus.data.entities.ActionStatus
import ru.ksart.timefocus.data.entities.UiAction
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.ui.extension.TIMER_INTERVAL
import ru.ksart.timefocus.ui.extension.displayTime
import ru.ksart.timefocus.ui.extension.loadSvgFromAsset

class ActionsViewHolder(
    private val binding: ItemActionsBinding,
    private val onClick: (UiAction<ActionWithInfo>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: ActionWithInfo? = null
    private var isClick = false
    private var coroutineScope: CoroutineScope? = null
    private val attachListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(p0: View?) {
//            Timber.tag("tag153").d("ActionsViewHolder: coroutineScope attached")
            startShowTimer()
        }

        override fun onViewDetachedFromWindow(p0: View?) {
//            Timber.tag("tag153").d("ActionsViewHolder: coroutineScope Detached")
            coroutineScope?.cancel()
            coroutineScope = null
            binding.root.removeOnAttachStateChangeListener(this)
        }
    }

    init {
        binding.run {
            start.setOnClickListener {
                item?.let { action ->
                    if (isClick) {
                        isClick = false
                        onClick(UiAction.Click(action))
                    }
                }
            }
            stop.setOnClickListener {
                item?.let { action ->
                    if (isClick) {
                        isClick = false
                        onClick(UiAction.Stop(action))
                    }
                }
            }
        }
    }

    private fun startShowTimer() {
//        Timber.tag("tag153").d("ActionsViewHolder: coroutineScope init")
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        coroutineScope?.launch {
//            Timber.tag("tag153").d("ActionsViewHolder: coroutineScope start")
            while (isActive) {
                binding.run {
                    item?.let {
                        timer.text = it.current.displayTime()
                        val timeAll = it.current + (it.times ?: 0)
                        binding.times.text = timeAll.displayTime()
                    }
                }
                delay(TIMER_INTERVAL)
            }
        }
        // Отписываем слушатель по окончании Job
        binding.root.removeOnAttachStateChangeListener(attachListener)
    }

    fun onBind(item: ActionWithInfo) {
        this.item = item
        isClick = true
        binding.run {
            name.text = item.name
            icon.loadSvgFromAsset(item.icon)
            icon.setColorFilter(item.color, PorterDuff.Mode.SRC_IN)
            start.setImageResource(if (item.status == ActionStatus.ACTIVE) R.drawable.ic_pause_24 else R.drawable.ic_start_24)
            start.setColorFilter(
                if (item.status == ActionStatus.ACTIVE) Color.BLUE else Color.GREEN,
                PorterDuff.Mode.SRC_IN
            )
            timer.text = item.current.displayTime()
//            times.text = item.times.displayTime()
        }
        // Добавляем слушатель, который будет отменять
        // корутину, если вьюха откреплена
        binding.root.addOnAttachStateChangeListener(attachListener)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (UiAction<ActionWithInfo>) -> Unit
        ) = ItemActionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { ActionsViewHolder(it, onClick) }
    }
}
