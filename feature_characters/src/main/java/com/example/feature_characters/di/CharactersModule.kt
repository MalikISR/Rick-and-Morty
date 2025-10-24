package com.example.feature_characters.di

import com.example.feature_characters.ui.characters.CharactersViewModel
import com.example.feature_characters.ui.details.CharacterDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val charactersModule = module {
    viewModel { CharactersViewModel(get(), get()) }
    viewModel { CharacterDetailsViewModel(get()) }
}
