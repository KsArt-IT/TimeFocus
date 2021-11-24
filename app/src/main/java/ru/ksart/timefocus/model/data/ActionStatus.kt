package ru.ksart.timefocus.model.data

import androidx.annotation.StringRes
import ru.ksart.timefocus.R

enum class ActionStatus(
    @StringRes val nameId: Int
) {
    Active(R.string.action_status_active),
    Pause(R.string.action_status_pause),
    Stop(R.string.action_status_stop)
}
