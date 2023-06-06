package com.aquispe.apprickandmorty.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoApiModel(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?,
)
