package com.aquispe.apprickandmorty.data.remote.service

import com.aquispe.apprickandmorty.data.remote.model.CharactersApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(): CharactersApiModel

    @GET("character")
    suspend fun getCharactersByPage(
        @Query("page")
        page: Int
    ): CharactersApiModel

    @GET("character")
    suspend fun getCharactersByFilter(
        @Query("name")
        name: String,
        @Query("status")
        status: String,
        @Query("species")
        species: String,
        @Query("type")
        type: String,
        @Query("gender")
        gender: String,
    ): CharactersApiModel
}
