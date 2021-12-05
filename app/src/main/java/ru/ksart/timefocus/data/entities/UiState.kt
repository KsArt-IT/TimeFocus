package ru.ksart.timefocus.data.entities

sealed class UiState<out T : Any> {

    data class Success<out T : Any>(val data: T) : UiState<T>()

    object Loading : UiState<Nothing>()

}
