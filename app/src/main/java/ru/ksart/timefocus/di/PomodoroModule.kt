package ru.ksart.timefocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ksart.timefocus.data.repositories.PomodoroImpl
import ru.ksart.timefocus.domain.repositories.Pomodoro
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PomodoroModule {

    @Binds
    @Singleton
    fun providePomodoro(impl: PomodoroImpl): Pomodoro

}
