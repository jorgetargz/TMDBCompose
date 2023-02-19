package com.jorgetargz.tmdbcompose.data.local

import androidx.room.*
import com.jorgetargz.tmdbcompose.data.models.entitys.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies order by popularity DESC")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(toDataEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

    @Query("Delete from movies where id = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM movies")
    fun deleteAll()
}