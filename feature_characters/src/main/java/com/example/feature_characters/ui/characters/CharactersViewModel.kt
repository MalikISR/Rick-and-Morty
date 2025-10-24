package com.example.feature_characters.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.model.Character
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.usecase.SearchCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

data class CharactersUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private val _selectedStatus = MutableStateFlow<String?>(null)
    val selectedStatus: StateFlow<String?> = _selectedStatus.asStateFlow()

    private val _selectedGender = MutableStateFlow<String?>(null)
    val selectedGender: StateFlow<String?> = _selectedGender.asStateFlow()

    private val _selectedSpecies = MutableStateFlow<String?>(null)
    val selectedSpecies: StateFlow<String?> = _selectedSpecies.asStateFlow()

    fun onStatusChanged(status: String?) {
        _selectedStatus.value = status
    }

    fun onGenderChanged(gender: String?) {
        _selectedGender.value = gender
    }

    fun onSpeciesChanged(species: String?) {
        _selectedSpecies.value = species
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    val characters: Flow<PagingData<Character>> =
        combine(
            _searchQuery.debounce(300),
            _selectedStatus,
            _selectedGender,
            _selectedSpecies
        ) { query, status, gender, species ->
            FilterParams(query, status, gender, species)
        }.flatMapLatest { params ->
            if (params.isEmpty()) {
                getCharactersUseCase().cachedIn(viewModelScope)
            } else {
                flow {
                    val results = searchCharactersUseCase(
                        query = params.query,
                        status = params.status,
                        gender = params.gender,
                        species = params.species
                    )
                    emit(PagingData.from(results))
                }.flowOn(Dispatchers.IO)
            }
        }

    private data class FilterParams(
        val query: String,
        val status: String?,
        val gender: String?,
        val species: String?
    ) {
        fun isEmpty() = query.isEmpty() && status == null && gender == null && species == null
    }
}
