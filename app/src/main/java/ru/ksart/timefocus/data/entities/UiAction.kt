package ru.ksart.timefocus.data.entities

import ru.ksart.timefocus.data.db.models.ActionNames

sealed class UiAction<out T : Any> {
    class Select(val actionNames: ActionNames) : UiAction<Nothing>()
    class Click<out T : Any>(val data: T) : UiAction<T>()
    class Stop<out T : Any>(val data: T) : UiAction<T>()

    //    class Start(val action: ActionWithInfo): UiAction()
//    class Pause(val action: ActionWithInfo): UiAction()
}
/*
sealed class UiState<out T : Any> {

    data class Success<out T : Any>(val data: T) : UiState<T>()

    data class Next(@LayoutRes val screen: Int = -1) : UiState<Nothing>()

    data class Loading(val isLoading: Boolean = true) : UiState<Nothing>()

    data class Error(val message: String?) : UiState<Nothing>()

}
*/
