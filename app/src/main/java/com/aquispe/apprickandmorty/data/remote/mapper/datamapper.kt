package com.aquispe.apprickandmorty.data.remote.mapper

import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import com.aquispe.apprickandmorty.data.local.database.entities.LocationDbModel
import com.aquispe.apprickandmorty.data.local.database.entities.OriginDbModel
import com.aquispe.apprickandmorty.data.remote.model.*
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.domain.model.Characters
import com.aquispe.apprickandmorty.domain.model.Info
import com.aquispe.apprickandmorty.domain.model.Location
import com.aquispe.apprickandmorty.domain.model.Origin

fun CharactersResponse.toDomain() = Characters(
    info = info.toDomain(),
    results = results.map {
        it.toDomain()
    }
)

fun InfoApiModel.toDomain() = Info(
    count = count,
    pages = pages,
    next = next,
    prev = prev
)

fun CharacterApiModel.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toDomain(),
    location = location.toDomain(),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun OriginApiModel.toDomain() = Origin(
    name = name,
    url = url
)

fun LocationApiModel.toDomain() = Location(
    name = name,
    url = url
)

fun CharacterApiModel.toDbModel() = CharacterDbModel(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.toDbModel(),
    location = location.toDbModel(),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun OriginApiModel.toDbModel() = OriginDbModel(
    name = name,
    url = url
)

fun LocationApiModel.toDbModel() = LocationDbModel(
    name = name,
    url = url
)
