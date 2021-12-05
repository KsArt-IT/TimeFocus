package ru.ksart.timefocus.domain.repositories

import ru.ksart.timefocus.domain.entities.IconChoice

interface IconsRepository {
    suspend fun requestIcons(): List<IconChoice>
}
