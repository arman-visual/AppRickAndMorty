package com.aquispe.apprickandmorty.data.remote.model

data class InfoApiModel(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?
)