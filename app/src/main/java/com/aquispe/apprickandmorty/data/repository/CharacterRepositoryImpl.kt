package com.aquispe.apprickandmorty.data.repository

import androidx.paging.*
import arrow.core.Either
import arrow.core.right
import com.aquispe.apprickandmorty.data.local.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.CharacterDbModel
import com.aquispe.apprickandmorty.data.local.toDomain
import com.aquispe.apprickandmorty.data.paging.CharacterRemoteMediator
import com.aquispe.apprickandmorty.data.remote.mapper.toDomain
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDatabase: CharacterDatabase,
    private val characterService: CharacterService
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                getCharactersPagingSource()
            },
            remoteMediator = getCharacterRemoteMediator()
        ).flow.map { pagingData ->
            pagingData.map { characterDbModel ->
                characterDbModel.toDomain()
            }
        }

    override fun getCharactersPagingSource(): PagingSource<Int, CharacterDbModel> {
        return characterDatabase.characterDbModelDao().getAllCharacters()
    }

    override fun getCharacterRemoteMediator(): CharacterRemoteMediator {
        return CharacterRemoteMediator(
            characterService,
            characterDatabase,
        )
    }

    override suspend fun getCharactersById(id: Int): Either<Throwable,com.aquispe.apprickandmorty.domain.model.Character> {
        val characterById = characterDatabase.characterDbModelDao().getCharacterById(id)?.toDomain()
        if(characterById != null) return characterById.right()

        return Either.catch {
            characterService.getCharacterById(id).toDomain()
        }
    }
}