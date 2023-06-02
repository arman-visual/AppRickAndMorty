package com.aquispe.apprickandmorty.data.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.model.CharactersResponse

interface CharacterService {

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id")
        id: Int,
    ): CharacterApiModel

    @GET("character")
    suspend fun getCharactersByPage(
        @Query("page")
        page: Int,
    ): CharactersResponse

    @GET("character")
    suspend fun getCharactersByFilter(
        @Query("page")
        page: Int,
        @Query("name")
        name: String,
        @Query("status")
        status: String,
        @Query("gender")
        gender: String,
    ): CharactersResponse
}
