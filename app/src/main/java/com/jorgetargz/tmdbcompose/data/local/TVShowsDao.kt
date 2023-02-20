package com.jorgetargz.tmdbcompose.data.local

import androidx.room.*
import com.jorgetargz.tmdbcompose.data.local.common.Constantes
import com.jorgetargz.tmdbcompose.data.models.entitys.TVShowEntity

@Dao
interface TVShowsDao {

    @Query(Constantes.SELECT_SHOWS_ORDER_BY_POPULARITY)
    fun getAll(): List<TVShowEntity>

    @Query(Constantes.SELECT_SHOWS_BY_ID)
    fun getById(id: Int): TVShowEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(toDataEntity: TVShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<TVShowEntity>)

    @Query(Constantes.DELETE_ALL_SHOWS)
    fun deleteAll()
}