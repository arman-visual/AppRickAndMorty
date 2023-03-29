package com.aquispe.apprickandmorty.data.remote.service

import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters(): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id")
        id: Int
    ): CharacterApiModel

    @GET("character")
    suspend fun getCharactersByPage(
        @Query("page")
        page: Int
    ): CharactersResponse

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
    ): CharactersResponse
}
