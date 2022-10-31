package ru.ksart.timefocus.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    /*
        @Provides
        fun providePreferencesHelper(preferences: SharedPreferences): PreferencesHelper =
            PreferencesHelperImpl(preferences)

    */
}
