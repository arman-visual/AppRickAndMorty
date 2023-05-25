package com.aquispe.apprickandmorty.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersResponse(
    val info: InfoApiModel,
    val results: List<CharacterApiModel>,
)
