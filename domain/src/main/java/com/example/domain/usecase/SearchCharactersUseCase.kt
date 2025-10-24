package com.example.domain.usecase

import com.example.core.model.Character
import com.example.domain.repository.CharacterRepository

class SearchCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(
        query: String = "",
        status: String? = null,
        gender: String? = null,
        species: String? = null
    ): List<Character> {
        return repository.getFilteredCharacters(
            name = query,
            status = status,
            gender = gender,
            species = species
        )
    }
}
