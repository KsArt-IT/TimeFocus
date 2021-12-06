package ru.ksart.timefocus.domain.usecase.actions

import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class GetActionNamesAllOrByGroupIdUseCase @Inject constructor(
    private val repository: ActionNamesRepository
) {

    suspend operator fun invoke(params: Long?): Results<List<ActionNames>> {
        return try {
            Results.Success(requestActionNames(params))
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    private suspend fun requestActionNames(params: Long?): List<ActionNames> {
        if (params != null) {
            val list = repository.getActionNamesByGroupId(params)
            if (list.isNotEmpty()) {
                return listOf(
                    // добавим пустой элемент для возврата из группы
                    ActionNames(
                        id = 0,
                        name = "",
                        description = null,
                        group = true,
                        groupId = null,
                        suspend = false,
                        suspendAll = false,
                        pomodoro = false,
                        pomodoroLong = false,
                        pomodoroSwitchId = null,
                        color = 0,
                        icon = "",
                        number = 0,
                        archive = false
                    )
                ) + list
            }
        }
        return repository.getActionsNamesWithoutGroup()
    }
}
