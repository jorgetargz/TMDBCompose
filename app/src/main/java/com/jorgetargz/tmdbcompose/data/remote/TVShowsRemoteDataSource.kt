package com.jorgetargz.tmdbcompose.data.remote

import com.jorgetargz.tmdbcompose.data.models.entitys.TVShowEntity
import com.jorgetargz.tmdbcompose.data.models.responses.TrendingTVShowResponse
import com.jorgetargz.tmdbcompose.data.remote.network.services.TVShowsService
import com.jorgetargz.tmdbcompose.utils.NetworkResult

/**
 * Fetches data using the TV Shows API Service
 */
class TVShowsRemoteDataSource(private val tvShowsService: TVShowsService) :
    BaseApiResponse() {

    suspend fun fetchTrendingTVShows(): NetworkResult<TrendingTVShowResponse> {
        return safeApiCall(apiCall = { tvShowsService.getPopularTVShows() })
    }

    suspend fun fetchTVShow(id: Int): NetworkResult<TVShowEntity> {
        return safeApiCall(apiCall = { tvShowsService.getTVShow(id) })
    }

}