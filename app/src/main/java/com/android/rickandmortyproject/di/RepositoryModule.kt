package com.android.rickandmortyproject.di

import com.android.rickandmortyproject.api.ApiService
import com.android.rickandmortyproject.database.AppDatabase
import com.android.rickandmortyproject.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideCharacterRepository(
        network: ApiService,
        database: AppDatabase
    ): CharacterRepository {
        return CharacterRepository(network = network, database = database)
    }
}