package ru.ksart.timefocus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.ksart.timefocus.domain.repositories.ActionsAddRepository
import ru.ksart.timefocus.data.repositories.ActionsAddRepositoryImpl
import ru.ksart.timefocus.domain.repositories.ActionsRepository
import ru.ksart.timefocus.data.repositories.ActionsRepositoryImpl
import ru.ksart.timefocus.domain.repositories.IconsRepository
import ru.ksart.timefocus.data.repositories.IconsRepositoryImpl
import ru.ksart.timefocus.domain.repositories.IntervalsRepository
import ru.ksart.timefocus.data.repositories.IntervalsRepositoryImpl
import ru.ksart.timefocus.domain.repositories.OnboardingRepository
import ru.ksart.timefocus.data.repositories.OnboardingRepositoryImpl
import ru.ksart.timefocus.domain.repositories.SettingsRepository
import ru.ksart.timefocus.data.repositories.SettingsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun provideOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    fun provideSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun provideIconsRepository(impl: IconsRepositoryImpl): IconsRepository

    @Binds
    fun provideActionAddRepository(impl: ActionsAddRepositoryImpl): ActionsAddRepository

    @Binds
    fun provideActionRepository(impl: ActionsRepositoryImpl): ActionsRepository

    @Binds
    fun provideIntervalsRepository(impl: IntervalsRepositoryImpl): IntervalsRepository
}
