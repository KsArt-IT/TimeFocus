package ru.ksart.timefocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ksart.timefocus.domain.repositories.Timers
import ru.ksart.timefocus.domain.entities.TimersImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TimerModule {

    @Binds
    @Singleton
    fun provideTimer(impl: TimersImpl): Timers
}
