package com.aquispe.apprickandmorty

import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.model.CharactersResponse
import com.aquispe.apprickandmorty.data.remote.model.InfoApiModel
import soy.gabimoreno.armandoquispe2.data.remote.service.CharacterService
import java.io.IOException

class FakeCharactersApi : CharacterService {

    private val searchCharacters = mutableListOf<CharacterApiModel>()
    private val allCharacters = mutableListOf<CharacterApiModel>()
    var failureMsg: String? = null

    fun addCharacters(characters: List<CharacterApiModel>) {
        this.searchCharacters.addAll(characters)
        this.allCharacters.addAll(characters)
    }

    fun clearCharacters() {
        searchCharacters.clear()
        allCharacters.clear()
    }

    override suspend fun getCharacterById(id: Int): CharacterApiModel {
        return searchCharacters.first { it.id == id }
    }

    override suspend fun getCharactersByPage(page: Int): CharactersResponse {
        failureMsg?.let {
            throw IOException(it)
        }

        return CharactersResponse(
            info = InfoApiModel(
                count = searchCharacters.size,
                pages = 42,
                next = null,
                prev = null
            ),
            results = allCharacters
        )
    }

    override suspend fun getCharactersByFilter(
        page: Int,
        name: String,
        status: String,
        gender: String
    ): CharactersResponse {

        failureMsg?.let {
            throw IOException(it)
        }

        return CharactersResponse(
            info = InfoApiModel(
                count = searchCharacters.size,
                pages = 42,
                next = null,
                prev = null
            ),
            results = searchCharacters
        )
    }
}
