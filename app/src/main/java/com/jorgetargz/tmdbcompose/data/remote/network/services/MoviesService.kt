package com.jorgetargz.tmdbcompose.data.remote.network.services

import com.jorgetargz.tmdbcompose.data.models.entitys.MovieEntity
import com.jorgetargz.tmdbcompose.data.models.responses.TrendingMovieResponse
import com.jorgetargz.tmdbcompose.data.remote.network.common.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API Services for Movies
 */
interface MoviesService {

    @GET(Constantes.TRENDING_MOVIES_URL)
    suspend fun getPopularMovies(): Response<TrendingMovieResponse>

    @GET(Constantes.MOVIE_URL)
    suspend fun getMovie(@Path(Constantes.MOVIE_ID) id: Int): Response<MovieEntity>
}