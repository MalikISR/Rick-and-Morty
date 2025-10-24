package com.example.rickandmorty.di

import com.example.data.di.dataModule
import com.example.domain.di.useCaseModule
import com.example.feature_characters.di.charactersModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        useCaseModule,
        charactersModule,
    )
}
