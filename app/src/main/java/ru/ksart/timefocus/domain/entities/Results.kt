package ru.ksart.timefocus.domain.entities

sealed class Results<out T : Any> {

    data class Success<out T : Any>(val data: T) : Results<T>()

    data class Error(val message: String) : Results<Nothing>()

//    object Loading : Results<Nothing>()

}
