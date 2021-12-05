package ru.ksart.timefocus.domain.usecase.actions

import ru.ksart.timefocus.domain.repositories.ActionsRepository
import javax.inject.Inject

class CreateActionUseCase @Inject constructor(
    private val repository: ActionsRepository,
) {

    suspend operator fun invoke(params: Long): Long = repository.addAction(params)

}
