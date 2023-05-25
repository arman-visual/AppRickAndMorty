package com.aquispe.apprickandmorty.domain.repository

import arrow.core.Either
import com.aquispe.apprickandmorty.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacterById(id: Int): Either<Throwable, Character>
}
