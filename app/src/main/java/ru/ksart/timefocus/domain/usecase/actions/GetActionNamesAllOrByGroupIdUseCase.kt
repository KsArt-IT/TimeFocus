package ru.ksart.timefocus.domain.usecase.actions

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import ru.ksart.timefocus.R
import ru.ksart.timefocus.data.db.models.ActionNames
import ru.ksart.timefocus.data.db.models.ActionWithInfo
import ru.ksart.timefocus.domain.entities.Results
import ru.ksart.timefocus.domain.repositories.ActionNamesRepository
import javax.inject.Inject

class GetActionNamesAllOrByGroupIdUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: ActionNamesRepository,
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
                        name = context.getString(R.string.action_back_name),
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
