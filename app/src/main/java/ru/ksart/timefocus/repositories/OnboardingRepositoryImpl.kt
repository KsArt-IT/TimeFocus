package ru.ksart.timefocus.repositories

import ru.ksart.timefocus.R
import ru.ksart.timefocus.model.data.OnboardingScreen
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
