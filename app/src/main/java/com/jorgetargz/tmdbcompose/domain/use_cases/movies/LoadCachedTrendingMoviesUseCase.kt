package com.jorgetargz.tmdbcompose.domain.use_cases.movies

import com.jorgetargz.tmdbcompose.data.MoviesRepository
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class LoadCachedTrendingMoviesUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<NetworkResult<List<Movie>>> {
        return repository.fetchTrendingMoviesCached()
    }
}