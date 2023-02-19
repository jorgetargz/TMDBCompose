package com.jorgetargz.tmdbcompose.ui.screens.movie_detail

import com.jorgetargz.tmdbcompose.domain.models.Movie


interface DetailsMovieContract {

    sealed class DetailsMovieEvent {
        class LoadMovie(val id: Int): DetailsMovieEvent()
        object ClearError : DetailsMovieEvent()
    }

    data class DetailsMovieState(
        val movie: Movie = Movie(),
        val isLoading : Boolean = false,
        val error: String? = null,
    )
}