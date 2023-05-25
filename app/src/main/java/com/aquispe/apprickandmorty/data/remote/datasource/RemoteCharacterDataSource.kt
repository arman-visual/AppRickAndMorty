package com.aquispe.apprickandmorty.data.remote.datasource

import arrow.core.Either
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.data.remote.mapper.toDomain
import soy.gabimoreno.armandoquispe2.data.remote.service.CharacterService
import javax.inject.Inject

class RemoteCharacterDataSource @Inject constructor(
    private val characterService: CharacterService,
) : CharacterDataSource {

    override suspend fun getCharacterById(id: Int): Either<Throwable, Character> {
        return Either.catch {
            characterService.getCharacterById(id).toDomain()
        }
    }
}
