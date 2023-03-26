package com.aquispe.apprickandmorty.data.remote.datasource

import arrow.core.Either
import com.aquispe.apprickandmorty.domain.model.Character

interface CharacterDataSource {

    suspend fun getCharactersByPage(page: Int): Either<Throwable, List<Character>>

    suspend fun getAllCharacters(): Either<Throwable, List<Character>>
}