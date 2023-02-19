package com.jorgetargz.tmdbcompose.data.remote

import com.jorgetargz.tmdbcompose.data.models.entitys.MovieEntity
import com.jorgetargz.tmdbcompose.data.models.responses.TrendingMovieResponse
import com.jorgetargz.tmdbcompose.data.remote.network.services.MoviesService
import com.jorgetargz.tmdbcompose.utils.NetworkResult

/**
 * Fetches data using the Movie API Service
 */
class MoviesRemoteDataSource(private val moviesService: MoviesService) :
    BaseApiResponse() {

    suspend fun fetchTrendingMovies(): NetworkResult<TrendingMovieResponse> {
        return safeApiCall(apiCall = { moviesService.getPopularMovies() })
    }

    suspend fun fetchMovie(id: Int): NetworkResult<MovieEntity> {
        return safeApiCall(apiCall = { moviesService.getMovie(id) })
    }

}