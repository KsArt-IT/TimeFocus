package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant
import ru.ksart.timefocus.data.entities.ActionStatus

@Entity(
    tableName = ActionsContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ActionNames::class,
            parentColumns = [ActionNamesContract.Columns.ID],
            childColumns = [ActionsContract.Columns.ACTION_NAMES_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(ActionsContract.Columns.ACTION_NAMES_ID)]
)
data class Actions(
    @PrimaryKey(autoGenerate = true)
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
)
