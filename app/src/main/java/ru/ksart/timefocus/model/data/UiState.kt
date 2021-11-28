package ru.ksart.timefocus.model.data

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean? = null,
)
