package com.jorgetargz.tmdbcompose.data.local.common

object Constantes {

    const val SELECT_MOVIES_ORDER_BY_POPULARITY = "SELECT * FROM movies order by popularity DESC"
    const val SELECT_MOVIES_BY_ID = "SELECT * FROM movies WHERE id = :id"
    const val DELETE_ALL_MOVIES = "DELETE FROM movies"

    const val SELECT_SHOWS_ORDER_BY_POPULARITY = "SELECT * FROM shows order by popularity DESC"
    const val SELECT_SHOWS_BY_ID = "SELECT * FROM shows WHERE id = :id"
    const val DELETE_ALL_SHOWS = "DELETE FROM shows"
}