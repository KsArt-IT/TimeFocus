package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(
    tableName = PomodoroIntervalsContract.TABLE_NAME,
    indices = [Index(PomodoroIntervalsContract.Columns.ACTION_NAMES_ID)],
    foreignKeys = [
        ForeignKey(
            entity = ActionNames::class,
            parentColumns = [ActionNamesContract.Columns.ID],
            childColumns = [PomodoroIntervalsContract.Columns.ACTION_NAMES_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class PomodoroIntervals(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.ACTION_NAMES_ID)
    val actionNamesId: Long = 0,
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.START_DATE)
    val startDate: Instant = Instant.now(),
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.STOP_DATE)
    val stopDate: Instant? = null,

//    @Ignore
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.TIME)
    var time: Long = 0,
//    @Ignore
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.TIME_DURATION)
    var timeDuration: Long = 0,
//    @Ignore
    @ColumnInfo(name = PomodoroIntervalsContract.Columns.STARTED)
    var isStarted: Boolean = false,
)
/*
{
    constructor(
        id: Long = 0,
        actionNamesId: Long = 0,
        startDate: Instant = Instant.now(),
        stopDate: Instant? = null,
    ) : this(
        id = id,
        actionNamesId = actionNamesId,
        startDate = startDate,
        stopDate = stopDate,

        time = 0,
        timeDuration = 0,
        isStarted = false,
    )
}
*/
