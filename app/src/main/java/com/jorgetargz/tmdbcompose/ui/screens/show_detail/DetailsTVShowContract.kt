package com.jorgetargz.tmdbcompose.ui.screens.show_detail

import com.jorgetargz.tmdbcompose.domain.models.TVShow


interface DetailsTVShowContract {

    sealed class DetailsTVShowEvent {
        class LoadTVShow(val id: Int) : DetailsTVShowEvent()
    }

    data class DetailsTVShowState(
        val tvShow: TVShow = TVShow(),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}