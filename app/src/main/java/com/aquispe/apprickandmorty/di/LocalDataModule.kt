package com.aquispe.apprickandmorty.di

import android.app.Application
import androidx.room.Room
import com.aquispe.apprickandmorty.data.local.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.LocalCharacterDataSource
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

}