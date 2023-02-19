package com.jorgetargz.tmdbcompose.ui.screens.trending_movies

import com.jorgetargz.tmdbcompose.domain.models.Movie


interface ListTrendingMoviesContract {

    sealed class ListTrendingMoviesEvent {
        class FilterTrendingMovies(val nombre: String) : ListTrendingMoviesEvent()
        object LoadTrendingMovies: ListTrendingMoviesEvent()
        object ClearError : ListTrendingMoviesEvent()

    }

    data class ListTrendingMoviesState(
        val movies: List<Movie> = emptyList(),
        val moviesFiltered: List<Movie> = emptyList(),
        val isLoading : Boolean = false,
        val error: String? = null,
    )
}