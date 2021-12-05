package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(
    tableName = ActionIntervalsContract.TABLE_NAME,
/*
    foreignKeys = [
        ForeignKey(
            entity = Actions::class,
            parentColumns = [ActionsContract.Columns.ID],
            childColumns = [ActionIntervalsContract.Columns.ACTIONS_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
*/
    foreignKeys = [
        ForeignKey(
            entity = ActionNames::class,
            parentColumns = [ActionNamesContract.Columns.ID],
            childColumns = [ActionIntervalsContract.Columns.ACTION_NAMES_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(ActionIntervalsContract.Columns.ACTION_ID), Index(ActionIntervalsContract.Columns.ACTION_NAMES_ID)]
)
data class ActionIntervals(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ActionIntervalsContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = ActionIntervalsContract.Columns.ACTION_ID)
    val actionsId: Long,
    @ColumnInfo(name = ActionIntervalsContract.Columns.ACTION_NAMES_ID)
    val actionNamesId: Long,
    @ColumnInfo(name = ActionIntervalsContract.Columns.START_DATE)
    val startDate: Instant,
    @ColumnInfo(name = ActionIntervalsContract.Columns.STOP_DATE)
    val stopDate: Instant = Instant.now(),
)
