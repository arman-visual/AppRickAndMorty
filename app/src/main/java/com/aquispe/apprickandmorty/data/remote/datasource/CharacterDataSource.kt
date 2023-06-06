package com.aquispe.apprickandmorty.data.remote.datasource

import arrow.core.Either
import com.aquispe.apprickandmorty.domain.model.Character

interface CharacterDataSource {
    suspend fun getCharacterById(id: Int): Either<Throwable, Character>
}
