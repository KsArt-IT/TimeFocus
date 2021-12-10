package ru.ksart.timefocus.data.entities

import androidx.annotation.StringRes
import ru.ksart.timefocus.R

enum class ActionMode(
    @StringRes val nameId: Int
) {
    NOTHING(R.string.action_mode_nothing), // не влияет
    STRICT(R.string.action_mode_strict), // строгий не отвлекать сообщениями
    PAUSE(R.string.action_mode_pause), // пауза, показать все оповещения
}
