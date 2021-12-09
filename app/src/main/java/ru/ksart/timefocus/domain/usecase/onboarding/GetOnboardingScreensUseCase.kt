package ru.ksart.timefocus.domain.usecase.onboarding

import ru.ksart.timefocus.domain.entities.OnboardingScreen
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.OnboardingRepository
import javax.inject.Inject

class GetOnboardingScreensUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(): Results<List<OnboardingScreen>> {
        return try {
            Results.Success(repository.requestScreens())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage)
        }
    }
}
