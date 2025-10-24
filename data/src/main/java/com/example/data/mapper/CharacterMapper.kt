package com.example.data.mapper

import com.example.core.model.Character
import com.example.core.model.LocationInfo
import com.example.data.local.CharacterEntity
import com.example.data.remote.dto.CharacterResponseDto

// DTO → Entity
fun CharacterResponseDto.toEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    originName = origin.name,
    originUrl = origin.url,
    locationName = location.name,
    locationUrl = location.url,
    image = image,
    episodes = episode,
    url = url,
    created = created
)

// Entity → Domain
fun CharacterEntity.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = LocationInfo(originName, originUrl),
    location = LocationInfo(locationName, locationUrl),
    image = image,
    episode = episodes,
    url = url,
    created = created
)

