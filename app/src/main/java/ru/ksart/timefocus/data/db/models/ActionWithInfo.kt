package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Ignore
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.entities.ActionMode
import ru.ksart.timefocus.data.entities.ActionStatus

data class ActionWithInfo(
    @ColumnInfo(name = ActionsContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = ActionsContract.Columns.ACTION_NAMES_ID)
    val actionNamesId: Long,
    @ColumnInfo(name = ActionsContract.Columns.STATUS)
    val status: ActionStatus = ActionStatus.ACTIVE,
    @ColumnInfo(name = ActionsContract.Columns.START_DATE)
    val startDate: Instant = Instant.now(),
    @ColumnInfo(name = ActionsContract.Columns.COMMENT)
    val comment: String? = null,

    @ColumnInfo(name = ActionNamesContract.Columns.NAME)
    val name: String,

    @ColumnInfo(name = ActionNamesContract.Columns.SUSPEND)
    val suspend: Boolean = false,
    @ColumnInfo(name = ActionNamesContract.Columns.SUSPEND_ALL)
    val suspendAll: Boolean = false,

    @ColumnInfo(name = ActionNamesContract.Columns.POMODORO)
    val pomodoro: Boolean = false,
    @ColumnInfo(name = ActionNamesContract.Columns.POMODORO_LONG)
    val pomodoroLong: Boolean = false,
    @ColumnInfo(name = ActionNamesContract.Columns.POMODORO_SWITCH_ID)
    val pomodoroSwitchId: Long? = null,

    @ColumnInfo(name = ActionNamesContract.Columns.MODE)
    val mode: ActionMode = ActionMode.NOTHING,

    @ColumnInfo(name = ActionNamesContract.Columns.COLOR)
    val color: Int = -0x1000000,//0xFF000000 Black
    @ColumnInfo(name = ActionNamesContract.Columns.ICON)
    val icon: String,

    @ColumnInfo(name = ActionsContract.Columns.TIMES_ACTION)
    val times: Long? = 0,

    @Ignore
    var current: Long = 0,
) {
    constructor(
        id: Long = 0,
        actionNamesId: Long,
        status: ActionStatus = ActionStatus.ACTIVE,
        startDate: Instant = Instant.now(),
        comment: String? = null,

        name: String,

        suspend: Boolean = false,
        suspendAll: Boolean = false,

        pomodoro: Boolean = false,
        pomodoroLong: Boolean = false,
        pomodoroSwitchId: Long? = null,

        mode: ActionMode = ActionMode.NOTHING,

        color: Int = -0x1000000,//0xFF000000 Black
        icon: String,
        times: Long? = 0,
    ) : this(
        id = id,
        actionNamesId = actionNamesId,
        status = status,
        startDate = startDate,
        comment = comment,
        name = name,
        suspend = suspend,
        suspendAll = suspendAll,
        pomodoro = pomodoro,
        pomodoroLong = pomodoroLong,
        pomodoroSwitchId = pomodoroSwitchId,

        mode = mode,

        color = color,
        icon = icon,
        times = times,

        current = 0,
    )
}
