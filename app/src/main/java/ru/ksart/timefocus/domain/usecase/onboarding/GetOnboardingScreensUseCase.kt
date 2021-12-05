package ru.ksart.timefocus.domain.usecase.onboarding

import ru.ksart.timefocus.domain.repositories.OnboardingRepository
import javax.inject.Inject

class GetOnboardingScreensUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke() = repository.requestScreens()
}
