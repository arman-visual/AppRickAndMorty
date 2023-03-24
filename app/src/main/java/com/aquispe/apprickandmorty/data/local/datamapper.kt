package com.aquispe.apprickandmorty.data.local

import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.domain.model.Location
import com.aquispe.apprickandmorty.domain.model.Origin

fun Location.toLocationDbModel() = LocationDbModel(
    name = name,
    url = url
)

fun Origin.toOriginDbModel() = OriginDbModel(
    name = name,
    url = url
)

fun Character.toCharacterDbModel() = CharacterDbModel(
    id = id,
    name = name,
    status = status,
    species = species,
    created = created,
    episode = episode,
    gender = gender,
    image = image,
    location = location.toLocationDbModel(),
    origin = origin.toOriginDbModel(),
    type = type,
    url = url
)

fun LocationDbModel.toDomain() = Location(
    name = name,
    url = url
)

fun OriginDbModel.toDomain() = Origin(
    name = name,
    url = url
)

fun CharacterDbModel.toDomain() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    created = created,
    episode = episode,
    gender = gender,
    image = image,
    location = location.toDomain(),
    origin = origin.toDomain(),
    type = type,
    url = url
)