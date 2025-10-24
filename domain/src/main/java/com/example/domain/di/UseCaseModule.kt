package com.example.domain.di

import com.example.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterDetailsUseCase(get()) }
    factory { FilterCharactersUseCase(get()) }
    factory { SearchCharactersUseCase(get()) }
}
