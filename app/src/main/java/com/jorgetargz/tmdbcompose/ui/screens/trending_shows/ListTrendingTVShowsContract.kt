package com.jorgetargz.tmdbcompose.ui.screens.trending_shows

import com.jorgetargz.tmdbcompose.domain.models.TVShow


interface ListTrendingTVShowsContract {

    sealed class ListTrendingTVShowsEvent {
        class FilterTrendingTVShows(val nombre: String) : ListTrendingTVShowsEvent()
        object LoadTrendingTVShows: ListTrendingTVShowsEvent()
    }

    data class ListTrendingTVShowsState(
        val tvShows: List<TVShow> = emptyList(),
        val tvShowsFiltered: List<TVShow> = emptyList(),
        val isLoading : Boolean = false,
        val error: String? = null,
    )
}