package ru.ksart.timefocus.model.data

import androidx.annotation.LayoutRes

sealed class UiAction<out T : Any> {
}
/*
sealed class UiState<out T : Any> {

    data class Success<out T : Any>(val data: T) : UiState<T>()

    data class Next(@LayoutRes val screen: Int = -1) : UiState<Nothing>()

    data class Loading(val isLoading: Boolean = true) : UiState<Nothing>()

    data class Error(val message: String?) : UiState<Nothing>()

}
*/
