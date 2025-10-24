package com.example.domain.repository

import androidx.paging.PagingData
import com.example.core.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharactersPaged(): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): Character?
    suspend fun getFilteredCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): List<Character>
}
