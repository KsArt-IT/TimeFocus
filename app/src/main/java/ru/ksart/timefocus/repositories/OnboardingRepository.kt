package ru.ksart.timefocus.repositories

import ru.ksart.timefocus.model.data.OnboardingScreen

interface OnboardingRepository {
    suspend fun requestScreens(): List<OnboardingScreen>
}
