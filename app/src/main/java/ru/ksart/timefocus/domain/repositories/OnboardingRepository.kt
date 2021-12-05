package ru.ksart.timefocus.domain.repositories

import ru.ksart.timefocus.domain.entities.OnboardingScreen

interface OnboardingRepository {
    suspend fun requestScreens(): List<OnboardingScreen>
}
