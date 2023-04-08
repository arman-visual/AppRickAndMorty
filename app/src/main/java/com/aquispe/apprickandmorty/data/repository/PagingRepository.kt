@file:OptIn(ExperimentalPagingApi::class)

package com.aquispe.apprickandmorty.data.repository

import androidx.paging.*
import com.aquispe.apprickandmorty.data.local.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.toDomain
import com.aquispe.apprickandmorty.data.paging.CharacterRemoteMediator
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val characterDatabase: CharacterDatabase,
    private val characterService: CharacterService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                characterDatabase.characterDbModelDao().getAllCharacters()
            },
            remoteMediator = CharacterRemoteMediator(
                characterService,
                characterDatabase,
            )
        ).flow.map { pagingData ->
            pagingData.map { characterDbModel ->
                characterDbModel.toDomain()
            }
        }
}