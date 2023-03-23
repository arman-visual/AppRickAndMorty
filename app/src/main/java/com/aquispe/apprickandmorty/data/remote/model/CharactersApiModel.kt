package com.aquispe.apprickandmorty.data.remote.model

data class CharactersApiModel(
    val info: InfoApiModel,
    val results: List<CharacterApiModel>
)