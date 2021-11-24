package ru.ksart.timefocus.model.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(
    tableName = ActionIntervalsContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Actions::class,
            parentColumns = [ActionsContract.Columns.ID],
            childColumns = [ActionIntervalsContract.Columns.ACTIONS_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(ActionIntervalsContract.Columns.ACTIONS_ID)]
)
data class ActionIntervals(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ActionIntervalsContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = ActionIntervalsContract.Columns.ACTIONS_ID)
    val actionsId: Long,
    @ColumnInfo(name = ActionIntervalsContract.Columns.START_DATE)
    val startDate: Instant,
    @ColumnInfo(name = ActionIntervalsContract.Columns.STOP_DATE)
    val stopDate: Instant,
)
