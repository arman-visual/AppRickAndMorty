package com.aquispe.apprickandmorty.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.aquispe.apprickandmorty.data.local.LocalCharacterDataSource
import com.aquispe.apprickandmorty.data.local.toCharacterDbModel
import com.aquispe.apprickandmorty.data.local.toDomain
import com.aquispe.apprickandmorty.data.remote.datasource.RemoteCharacterDataSource
import com.aquispe.apprickandmorty.domain.model.*
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val characterRemoteDataSource: RemoteCharacterDataSource,
    private val characterLocalDataSource: LocalCharacterDataSource
) {

    suspend fun getCharactersByPage(page: Int): Either<Throwable, Characters> {
        if (characterLocalDataSource.getCharacters().isEmpty()) {
            characterRemoteDataSource.getCharactersByPage(page).fold(
                {
                    return it.left()
                }, { characters ->

                })
        }
        return characterRemoteDataSource.getCharactersByPage(page)
    }

    suspend fun getAllCharacters(): Either<Throwable, List<Character>> {
        if (characterLocalDataSource.isEmpty()) {
            characterRemoteDataSource.getAllCharacters().fold(
                {
                    return it.left()
                }, { characters ->
                    characterLocalDataSource.saveCharacters(characters)
                })
        }

        return characterLocalDataSource.getCharacters().map { it.toDomain() }.right()
    }
}