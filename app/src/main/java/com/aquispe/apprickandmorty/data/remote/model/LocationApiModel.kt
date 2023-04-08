package com.aquispe.apprickandmorty.data.remote.model

@kotlinx.serialization.Serializable
data class LocationApiModel(
    val name: String,
    val url: String
)