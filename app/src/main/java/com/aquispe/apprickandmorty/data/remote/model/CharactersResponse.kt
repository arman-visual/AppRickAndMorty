package com.aquispe.apprickandmorty.data.remote.model

data class CharactersResponse(
    val info: InfoApiModel,
    val results: List<CharacterApiModel>
)