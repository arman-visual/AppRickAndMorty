package com.aquispe.apprickandmorty.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aquispe.apprickandmorty.data.local.database.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.aquispe.apprickandmorty.data.paging.CharacterRemoteMediator
import soy.gabimoreno.armandoquispe2.data.remote.service.CharacterService
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCharactersPager(
        characterDb: CharacterDatabase,
        characterApiService: CharacterService
    ): Pager<Int, CharacterDbModel> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = CharacterRemoteMediator(
                characterService = characterApiService,
                characterDatabase = characterDb,
            ),
            pagingSourceFactory = {
                characterDb.characterDbModelDao().getAllCharacters()
            }
        )
    }
}

private const val PAGE_SIZE = 20
