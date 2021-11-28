package ru.ksart.timefocus.repositories

import ru.ksart.timefocus.model.data.IconChoice

interface IconsRepository {
    suspend fun requestIcons(): List<IconChoice>
}
