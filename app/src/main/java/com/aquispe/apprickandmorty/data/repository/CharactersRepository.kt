package com.aquispe.apprickandmorty.data.repository

import arrow.core.Either
import com.aquispe.apprickandmorty.data.remote.datasource.RemoteCharacterDataSource
import com.aquispe.apprickandmorty.domain.model.Characters
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val characterRemoteDataSource: RemoteCharacterDataSource
) {

    suspend fun getCharactersByPage(page: Int): Either<Throwable, Characters> =
        characterRemoteDataSource.getCharactersByPage(page)
}