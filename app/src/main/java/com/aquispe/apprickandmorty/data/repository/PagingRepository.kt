package com.aquispe.apprickandmorty.data.repository

import androidx.paging.*
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import com.aquispe.apprickandmorty.data.local.datamapper.toDomain
import com.aquispe.apprickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.aquispe.apprickandmorty.data.paging.CharacterPagingSource
import com.aquispe.apprickandmorty.data.remote.mapper.toDomain
import com.aquispe.apprickandmorty.data.remote.service.CharacterService

import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val characterService: CharacterService,
    private val pager:Pager<Int, CharacterDbModel>,
) {

    fun getAllCharacters(): Flow<PagingData<Character>> =
        pager.flow.map { pagingData ->
            pagingData.map { characterDbModel ->
                characterDbModel.toDomain()
            }
        }

    fun searchCharacters(
        name: String,
        gender: String,
        status: String,
    ): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    characterService = characterService,
                    name = name,
                    gender = gender,
                    status = status
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { characterApiModel ->
                characterApiModel.toDomain()
            }
        }
}

private const val PAGE_SIZE = 20
