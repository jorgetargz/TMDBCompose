package com.jorgetargz.tmdbcompose.data.models.responses

import com.jorgetargz.tmdbcompose.data.models.entitys.MovieEntity

class TrendingMovieResponse(
    val results: List<MovieEntity>?
)