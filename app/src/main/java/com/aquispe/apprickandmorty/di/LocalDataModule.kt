package com.aquispe.apprickandmorty.di

import android.app.Application
import androidx.room.Room
import com.aquispe.apprickandmorty.data.local.LocalCharacterDataSource
import com.aquispe.apprickandmorty.data.local.database.CharacterDatabase
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
            "rick_and_morty_db",
        ).build()

    @Provides
    fun provideLocalCharacterDataSource(
        characterDatabase: CharacterDatabase,
    ): LocalCharacterDataSource = LocalCharacterDataSource(characterDatabase)

}
