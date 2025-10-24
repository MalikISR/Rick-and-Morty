package com.example.data.di

import androidx.room.Room
import com.example.data.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "rick_and_morty.db"
        ).build()
    }
    single { get<AppDatabase>().characterDao() }
}
