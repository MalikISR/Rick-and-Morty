package com.example.data.di

import com.example.data.repository.CharacterRepositoryImpl
import com.example.domain.repository.CharacterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl(
            api = get(),
            dao = get(),
            context = get()
        )
    }
}
