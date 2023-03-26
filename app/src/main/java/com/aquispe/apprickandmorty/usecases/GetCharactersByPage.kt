package com.aquispe.apprickandmorty.usecases

import arrow.core.Either
import com.aquispe.apprickandmorty.data.repository.CharactersRepository
import com.aquispe.apprickandmorty.domain.model.Character
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharactersByPage @Inject constructor(
    private val repository: CharactersRepository
) {
    suspend operator fun invoke(page: Int): Either<Throwable, List<Character>> =
        repository.getCharactersByPage(page)
}