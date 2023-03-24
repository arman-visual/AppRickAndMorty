package com.aquispe.apprickandmorty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aquispe.apprickandmorty.domain.model.Location
import com.aquispe.apprickandmorty.domain.model.Origin

@Entity
data class CharacterDbModel(
    @PrimaryKey
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val location: LocationDbModel,
    val name: String,
    val origin: OriginDbModel,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
