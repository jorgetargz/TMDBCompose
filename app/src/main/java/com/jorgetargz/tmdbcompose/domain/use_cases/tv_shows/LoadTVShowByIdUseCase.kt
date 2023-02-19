package com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows

import com.jorgetargz.tmdbcompose.data.TVShowsRepository
import com.jorgetargz.tmdbcompose.domain.models.TVShow
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class LoadTVShowByIdUseCase(
    private val repository: TVShowsRepository
) {
    operator fun invoke(id: Int): Flow<NetworkResult<TVShow>> {
        return repository.fetchTVShow(id)
    }
}