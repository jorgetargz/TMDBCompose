package com.jorgetargz.tmdbcompose.di

import androidx.room.Room
import com.jorgetargz.tmdbcompose.data.local.AppDatabase
import org.koin.dsl.module

private const val DB_NAME = "app.db"

val roomModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigrationFrom(1)
            .build()
    }

    single { (get() as AppDatabase).moviesDao() }
    single { (get() as AppDatabase).tvShowsDao() }
}