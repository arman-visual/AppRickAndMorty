package com.aquispe.apprickandmorty.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.aquispe.apprickandmorty.data.local.LocalCharacterDataSource
import com.aquispe.apprickandmorty.data.local.toDomain
import com.aquispe.apprickandmorty.data.remote.datasource.RemoteCharacterDataSource
import com.aquispe.apprickandmorty.domain.model.Character
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val characterRemoteDataSource: RemoteCharacterDataSource,
    private val characterLocalDataSource: LocalCharacterDataSource
) {
    suspend fun getCharactersByPage(page: Int): Either<Throwable, List<Character>> {
        if (characterLocalDataSource.isEmpty()) {
            characterRemoteDataSource.getCharactersByPage(page).fold(
                {
                    it.left()
                }, { characters ->
                    characterLocalDataSource.saveCharacters(characters)
                })
        }
        return characterLocalDataSource.getCharacters().map { it.toDomain() }.right()
    }

    suspend fun getAllCharacters(): Either<Throwable, List<Character>> {
        if (characterLocalDataSource.isEmpty()) {
            characterRemoteDataSource.getAllCharacters().fold(
                {
                    return it.left()
                }, { characters ->
                    characterLocalDataSource.saveCharacters(characters)
                    //Almacenar total de paginas?
                })
        }
        return characterLocalDataSource.getCharacters().map { it.toDomain() }.right()
    }
}