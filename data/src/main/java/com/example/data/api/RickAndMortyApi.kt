package com.example.data.api

import com.example.data.remote.dto.CharacterResponseDto
import com.example.data.remote.dto.CharacterListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("gender") gender: String? = null
    ): CharacterListResponseDto

    @GET("character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: Int): CharacterResponseDto
}
