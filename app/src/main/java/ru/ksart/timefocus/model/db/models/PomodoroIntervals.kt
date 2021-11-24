package ru.ksart.timefocus.model.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(
    tableName = PomodoroIntervalsContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Actions::class,
            parentColumns = [ActionsContract.Columns.ID],
            childColumns = [PomodoroIntervalsContract.Columns.ACTIONS_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(PomodoroIntervalsContract.Columns.ACTIONS_ID)]
)
data class PomodoroIntervals(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.ACTIONS_ID)
    val actionsId: Long,
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.START_DATE)
    val startDate: Instant,
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.STOP_DATE)
    val stopDate: Instant,
)
