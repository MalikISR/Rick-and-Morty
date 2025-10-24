package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.core.model.Character
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<PagingData<Character>> =
        repository.getCharactersPaged()
}
