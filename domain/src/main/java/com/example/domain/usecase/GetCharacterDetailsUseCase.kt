package com.example.domain.usecase

import com.example.core.model.Character
import com.example.domain.repository.CharacterRepository

class GetCharacterDetailsUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Character? = repository.getCharacterById(id)
}
