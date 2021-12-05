package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = ActionSuspendContract.TABLE_NAME,
    primaryKeys = [
        ActionSuspendContract.Columns.ACTION_NAMES_ID,
        ActionSuspendContract.Columns.ACTION_NAMES_SUSPENDED_ID
    ],
    foreignKeys = [
        ForeignKey(
            entity = ActionNames::class,
            parentColumns = [ActionNamesContract.Columns.ID],
            childColumns = [ActionSuspendContract.Columns.ACTION_NAMES_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ActionNames::class,
            parentColumns = [ActionNamesContract.Columns.ID],
            childColumns = [ActionSuspendContract.Columns.ACTION_NAMES_SUSPENDED_ID]
        )
    ],
    indices = [
        Index(ActionSuspendContract.Columns.ACTION_NAMES_ID),
        Index(ActionSuspendContract.Columns.ACTION_NAMES_SUSPENDED_ID),
        Index(
            ActionSuspendContract.Columns.ACTION_NAMES_ID,
            ActionSuspendContract.Columns.ACTION_NAMES_SUSPENDED_ID,
            unique = true
        ),
    ],
)
data class ActionSuspend(
    @ColumnInfo(name = ActionSuspendContract.Columns.ACTION_NAMES_ID)
    val actionNamesId: Long,
    @ColumnInfo(name = ActionSuspendContract.Columns.ACTION_NAMES_SUSPENDED_ID)
    val actionNamesSuspendedId: Long,
)
