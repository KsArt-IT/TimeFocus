package ru.ksart.timefocus.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ksart.timefocus.model.db.ActionsDatabase
import ru.ksart.timefocus.model.db.ActionsDatabaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ActionsDatabase {
        return Room.databaseBuilder(
            context,
            ActionsDatabaseImpl::class.java,
            ActionsDatabaseImpl.DB_NAME
        )
            .build()
    }
}
