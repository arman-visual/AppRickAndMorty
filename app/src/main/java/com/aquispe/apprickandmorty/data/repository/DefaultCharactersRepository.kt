package com.aquispe.apprickandmorty.data.repository

import arrow.core.Either
import com.aquispe.apprickandmorty.data.remote.datasource.RemoteCharacterDataSource
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class DefaultCharactersRepository @Inject constructor(
    private val characterRemoteDataSource: RemoteCharacterDataSource,
) : CharacterRepository {

    override suspend fun getCharacterById(id: Int): Either<Throwable, Character> {
        return characterRemoteDataSource.getCharacterById(id)
    }
}
