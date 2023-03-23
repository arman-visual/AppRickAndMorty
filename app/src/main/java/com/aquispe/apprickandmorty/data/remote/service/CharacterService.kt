package com.aquispe.apprickandmorty.data.remote.service

import com.aquispe.apprickandmorty.data.remote.model.CharactersApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("/api/character")
    suspend fun getCharactersByPage(
        @Query("page")
        page: Int
    ): CharactersApiModel
}
