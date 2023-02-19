package com.jorgetargz.tmdbcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgetargz.tmdbcompose.data.models.entitys.MovieEntity
import com.jorgetargz.tmdbcompose.data.models.entitys.TVShowEntity

@Database(
    entities = [MovieEntity::class, TVShowEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun tvShowsDao(): TVShowsDao
}
