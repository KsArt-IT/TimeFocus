package ru.ksart.timefocus.data.db.models

object WaterContract {
    const val TABLE_NAME = "water_volume" // список интервалов в активности в помидорах

    object Columns {
        const val ID = "id"
        const val VOLUME = "volume" // Объем за раз
        const val DATE = "date" // Дата
    }
}
