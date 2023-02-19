package com.jorgetargz.tmdbcompose.di

import androidx.room.Room
import com.jorgetargz.tmdbcompose.data.local.AppDatabase
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigrationFrom(1)
            .build()
    }

    single { (get() as AppDatabase).moviesDao() }
    single { (get() as AppDatabase).tvShowsDao() }
}