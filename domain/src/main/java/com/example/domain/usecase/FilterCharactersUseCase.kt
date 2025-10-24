package com.example.domain.usecase

import com.example.core.model.Character
import com.example.domain.repository.CharacterRepository

class FilterCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): List<Character> =
        repository.getFilteredCharacters(
            name = name,
            status = status,
            species = species,
            gender = gender
        )
}
