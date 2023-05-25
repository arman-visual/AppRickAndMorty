package com.aquispe.apprickandmorty.di

import android.app.Application
import androidx.room.Room
import com.aquispe.apprickandmorty.data.local.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.LocalCharacterDataSource
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import com.aquispe.apprickandmorty.data.repository.CharacterRepository
import com.aquispe.apprickandmorty.data.repository.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun characterDataBaseProvider(application: Application): CharacterDatabase =
        Room.databaseBuilder(
            application,
            CharacterDatabase::class.java,
            "character-db",
        ).build()

    @Provides
    fun provideLocalCharacterDataSource(
        characterDatabase: CharacterDatabase
    ): LocalCharacterDataSource = LocalCharacterDataSource(characterDatabase)

    @Provides
    fun provideCharacterRepository(
        characterDatabase: CharacterDatabase,
        characterService: CharacterService
    ): CharacterRepository = CharacterRepositoryImpl(characterDatabase = characterDatabase, characterService = characterService)
}