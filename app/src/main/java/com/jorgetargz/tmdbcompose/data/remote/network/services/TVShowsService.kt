package com.jorgetargz.tmdbcompose.data.remote.network.services

import com.jorgetargz.tmdbcompose.data.models.entitys.TVShowEntity
import com.jorgetargz.tmdbcompose.data.models.responses.TrendingTVShowResponse
import com.jorgetargz.tmdbcompose.data.remote.network.common.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API Services for TV Shows
 */
interface TVShowsService {

    @GET(Constantes.TRENDING_TV_SHOWS_URL)
    suspend fun getPopularTVShows(): Response<TrendingTVShowResponse>

    @GET(Constantes.TV_SHOW_URL)
    suspend fun getTVShow(@Path(Constantes.TV_ID) id: Int): Response<TVShowEntity>
}