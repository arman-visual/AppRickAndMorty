package com.aquispe.apprickandmorty.usecases

import arrow.core.Either
import com.aquispe.apprickandmorty.data.repository.CharacterRepository
import com.aquispe.apprickandmorty.domain.model.Character
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Either<Throwable, Character> =
        repository.getCharactersById(id)
}