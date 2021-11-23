package ru.ksart.timefocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.ksart.timefocus.repositories.OnboardingRepository
import ru.ksart.timefocus.repositories.OnboardingRepositoryImpl
import ru.ksart.timefocus.repositories.SettingsRepository
import ru.ksart.timefocus.repositories.SettingsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    fun provideSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
