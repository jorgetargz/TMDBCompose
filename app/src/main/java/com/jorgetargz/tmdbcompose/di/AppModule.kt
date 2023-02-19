package com.jorgetargz.tmdbcompose.di

import com.jorgetargz.tmdbcompose.data.MoviesRepository
import com.jorgetargz.tmdbcompose.data.TVShowsRepository
import com.jorgetargz.tmdbcompose.data.remote.MoviesRemoteDataSource
import com.jorgetargz.tmdbcompose.data.remote.TVShowsRemoteDataSource
import com.jorgetargz.tmdbcompose.domain.use_cases.movies.LoadCachedMovieByIdUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.movies.LoadCachedTrendingMoviesUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.movies.LoadMovieByIdUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.movies.LoadTrendingMoviesUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows.LoadCachedTVShowByIdUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows.LoadCachedTrendingTVShowsUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows.LoadTVShowByIdUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows.LoadTrendingTVShowsUseCase
import com.jorgetargz.tmdbcompose.ui.screens.trending_movies.ListTrendingMoviesViewModel
import com.jorgetargz.tmdbcompose.ui.screens.trending_shows.ListTrendingTVShowsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single { MoviesRemoteDataSource(get()) } bind MoviesRemoteDataSource::class
    single { TVShowsRemoteDataSource(get()) } bind TVShowsRemoteDataSource::class

    single { MoviesRepository(get(), get()) } bind MoviesRepository::class
    single { TVShowsRepository(get(), get()) } bind TVShowsRepository::class

    single { LoadCachedMovieByIdUseCase(get()) } bind LoadCachedMovieByIdUseCase::class
    single { LoadCachedTrendingMoviesUseCase(get()) } bind LoadCachedTrendingMoviesUseCase::class
    single { LoadMovieByIdUseCase(get()) } bind LoadMovieByIdUseCase::class
    single { LoadTrendingMoviesUseCase(get()) } bind LoadTrendingMoviesUseCase::class

    single { LoadCachedTrendingTVShowsUseCase(get()) } bind LoadCachedTrendingTVShowsUseCase::class
    single { LoadTrendingTVShowsUseCase(get()) } bind LoadTrendingTVShowsUseCase::class
    single { LoadCachedTVShowByIdUseCase(get()) } bind LoadCachedTVShowByIdUseCase::class
    single { LoadTVShowByIdUseCase(get()) } bind LoadTVShowByIdUseCase::class

    viewModelOf(::ListTrendingMoviesViewModel)
    viewModelOf(::ListTrendingTVShowsViewModel)
}
