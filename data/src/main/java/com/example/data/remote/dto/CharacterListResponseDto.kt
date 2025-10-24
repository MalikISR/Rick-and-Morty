package com.example.data.remote.dto

data class CharacterListResponseDto(
    val info: InfoDto,
    val results: List<CharacterResponseDto>
)

data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
