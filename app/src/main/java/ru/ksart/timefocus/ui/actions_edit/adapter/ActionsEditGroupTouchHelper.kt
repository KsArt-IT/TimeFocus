package ru.ksart.timefocus.ui.actions_edit.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import ru.ksart.timefocus.data.db.models.ActionNames

class ActionsEditGroupTouchHelper(
    onDragged: (ActionNames) -> Unit,
    onSwiped: (ActionNames) -> Unit,
) : ItemTouchHelper(ActionsEditGroupTouchCallback(onDragged, onSwiped))
