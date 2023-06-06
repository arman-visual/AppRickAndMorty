package com.aquispe.apprickandmorty.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationApiModel(
    val name: String,
    val url: String,
)
