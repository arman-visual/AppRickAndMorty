package com.aquispe.apprickandmorty.data.remote.datasource

import arrow.core.Either
import com.aquispe.apprickandmorty.data.remote.mapper.toDomain
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import com.aquispe.apprickandmorty.domain.model.*
import javax.inject.Inject

class RemoteCharacterDataSource @Inject constructor(
    private val characterService: CharacterService
) : CharacterDataSource {

    override suspend fun getCharactersByPage(page: Int): Either<Throwable, List<Character>> {
        return Either.catch {
            characterService.getCharactersByPage(page)
                .results.map { it.toDomain() }
        }
    }

    override suspend fun getAllCharacters(): Either<Throwable, List<Character>> {
        return Either.catch {
            characterService.getAllCharacters().results.map { it.toDomain() }
        }
    }

    override suspend fun getCharacterById(id: Int): Either<Throwable, Character> {
        return Either.catch {
            characterService.getCharacterById(id).toDomain()
        }
    }
}