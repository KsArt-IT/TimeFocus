package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

data class ActionIntervalsWithInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ActionIntervalsContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = ActionIntervalsContract.Columns.ACTION_ID)
    val actionId: Long,
    @ColumnInfo(name = ActionIntervalsContract.Columns.ACTION_NAMES_ID)
    val actionNamesId: Long,
    @ColumnInfo(name = ActionIntervalsContract.Columns.START_DATE)
    val startDate: Instant,
    @ColumnInfo(name = ActionIntervalsContract.Columns.STOP_DATE)
    val stopDate: Instant = Instant.now(),


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

    @ColumnInfo(name = ActionNamesContract.Columns.COLOR)
    val color: Int = -0x1000000,//0xFF000000 Black
    @ColumnInfo(name = ActionNamesContract.Columns.ICON)
    val icon: String,
)
