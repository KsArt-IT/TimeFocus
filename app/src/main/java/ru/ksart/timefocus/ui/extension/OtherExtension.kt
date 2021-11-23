package ru.ksart.timefocus.ui.extension

// проверка для when и sealed class на вхождение всех членов класса
val <T> T.exhaustive: T
    get() = this
