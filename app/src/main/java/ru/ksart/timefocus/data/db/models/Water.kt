package ru.ksart.timefocus.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = WaterContract.TABLE_NAME)
data class Water(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WaterContract.Columns.ID)
    val id: Long = 0,
    @ColumnInfo(name = WaterContract.Columns.DATE)
    val date: Instant = Instant.now(),
    @ColumnInfo(name = WaterContract.Columns.VOLUME)
    val volume: Long = 0,
)
