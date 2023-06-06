package com.aquispe.apprickandmorty.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_character")
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
    val url: String,
)
