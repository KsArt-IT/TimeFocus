package ru.ksart.timefocus.domain.usecase.icons

import ru.ksart.timefocus.domain.entities.IconChoice
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.IconsRepository
import javax.inject.Inject

class GetIconsUseCase @Inject constructor(
    private val repository: IconsRepository,
) {

    suspend operator fun invoke(): Results<List<IconChoice>> {
        return try {
            Results.Success(repository.requestIcons())
        } catch (e: Exception) {
            Results.Error(e.localizedMessage)
        }
    }

}
