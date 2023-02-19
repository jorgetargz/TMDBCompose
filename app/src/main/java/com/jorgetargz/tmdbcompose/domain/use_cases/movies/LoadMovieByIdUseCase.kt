package com.jorgetargz.tmdbcompose.domain.use_cases.movies

import com.jorgetargz.tmdbcompose.data.MoviesRepository
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class LoadMovieByIdUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<NetworkResult<Movie>> {
        return repository.fetchMovie(id)
    }
}