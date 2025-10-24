package com.example.feature_characters.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Character
import com.example.domain.usecase.GetCharacterDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CharacterDetailsUiState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class CharacterDetailsViewModel(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailsUiState())
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val character = getCharacterDetailsUseCase(id)
                _uiState.value = CharacterDetailsUiState(character = character)
            } catch (e: Exception) {
                _uiState.value = CharacterDetailsUiState(error = e.message)
            }
        }
    }
}
