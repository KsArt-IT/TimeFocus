package ru.ksart.timefocus.data.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = ActionNamesContract.TABLE_NAME,
    indices = [
        Index(ActionNamesContract.Columns.NAME, unique = true),
        Index(ActionNamesContract.Columns.GROUP_ID)
    ],
    foreignKeys = [
        ForeignKey(
            entity = ActionNames::class,
            parentColumns = [ActionNamesContract.Columns.ID],
            childColumns = [ActionNamesContract.Columns.GROUP_ID],
            onDelete = ForeignKey.CASCADE // при удаление главной активноасти-группы, удалятся и все активности входящие в группу
        )
    ],
)
data class ActionNames(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ActionNamesContract.Columns.ID)
    val id: Long = 0,

    @ColumnInfo(name = ActionNamesContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = ActionNamesContract.Columns.DESCRIPTION)
    val description: String? = null,

    @ColumnInfo(name = ActionNamesContract.Columns.GROUP)
    val group: Boolean = false,
    @ColumnInfo(name = ActionNamesContract.Columns.GROUP_ID)
    val groupId: Long? = null,
    @ColumnInfo(name = ActionNamesContract.Columns.GROUP_COUNT)
    val groupCount: Int = 0,

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

    @ColumnInfo(name = ActionNamesContract.Columns.NUMBER)
    val number: Int = 0,
    @ColumnInfo(name = ActionNamesContract.Columns.ARCHIVE)
    val archive: Boolean = false,
): Parcelable
