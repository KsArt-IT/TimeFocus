package ru.ksart.timefocus.data.repositories

import ru.ksart.timefocus.R
import ru.ksart.timefocus.domain.entities.OnboardingScreen
import ru.ksart.timefocus.domain.repositories.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor() : OnboardingRepository {

    private val listScreens: List<OnboardingScreen> = listOf(
        OnboardingScreen(
            title = R.string.onboarding_title_first,
            text = R.string.onboarding_text_first,
            image = R.drawable.onboarding_image_first,
        ),
        OnboardingScreen(
            title = R.string.onboarding_title_second,
            text = R.string.onboarding_text_second,
            image = R.drawable.onboarding_image_first,
        ),
    )

    override suspend fun requestScreens(): List<OnboardingScreen> = listScreens

}
