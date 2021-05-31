package com.android.rickandmortyproject.di

import android.app.Application
import androidx.room.Room
import com.android.rickandmortyproject.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "RickAndMorty.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}