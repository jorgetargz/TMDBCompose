package com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows

import com.jorgetargz.tmdbcompose.data.TVShowsRepository
import com.jorgetargz.tmdbcompose.domain.models.TVShow
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class LoadCachedTrendingTVShowsUseCase(
    private val repository: TVShowsRepository
) {
    operator fun invoke(): Flow<NetworkResult<List<TVShow>>> {
        return repository.fetchTrendingTVShowsCached()
    }
}