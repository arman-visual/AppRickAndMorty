package com.aquispe.apprickandmorty.data.remote.model

@kotlinx.serialization.Serializable
data class OriginApiModel(
    val name: String,
    val url: String
)