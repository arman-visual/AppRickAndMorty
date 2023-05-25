package com.aquispe.apprickandmorty.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import arrow.core.Either
import com.aquispe.apprickandmorty.data.local.CharacterDbModel
import com.aquispe.apprickandmorty.data.paging.CharacterRemoteMediator
import com.aquispe.apprickandmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharactersPagingSource(): PagingSource<Int, CharacterDbModel>
    fun getCharacterRemoteMediator(): CharacterRemoteMediator
    suspend fun getCharactersById(id: Int): Either<Throwable, Character>
    fun getAllCharacters(): Flow<PagingData<Character>>
}