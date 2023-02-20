package com.jorgetargz.tmdbcompose.data.local

import androidx.room.*
import com.jorgetargz.tmdbcompose.data.local.common.Constantes
import com.jorgetargz.tmdbcompose.data.models.entitys.MovieEntity

@Dao
interface MoviesDao {

    @Query(Constantes.SELECT_MOVIES_ORDER_BY_POPULARITY)
    fun getAll(): List<MovieEntity>

    @Query(Constantes.SELECT_MOVIES_BY_ID)
    fun getById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(toDataEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

    @Query(Constantes.DELETE_ALL_MOVIES)
    fun deleteAll()
}