package ru.ksart.timefocus.data.entities

import androidx.annotation.StringRes
import ru.ksart.timefocus.R

enum class ActionStatus(
    @StringRes val nameId: Int
) {
    ACTIVE(R.string.action_status_active),
    PAUSE(R.string.action_status_pause),
    STOP(R.string.action_status_stop),
}
