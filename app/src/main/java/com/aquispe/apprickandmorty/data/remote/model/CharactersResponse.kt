package com.aquispe.apprickandmorty.data.remote.model

@kotlinx.serialization.Serializable
data class CharactersResponse(
    val info: InfoApiModel,
    val results: List<CharacterApiModel>
)