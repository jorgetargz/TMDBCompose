package com.jorgetargz.tmdbcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jorgetargz.tmdbcompose.ui.common.Constants
import com.jorgetargz.tmdbcompose.ui.screens.movie_detail.DetailsMovieScreen
import com.jorgetargz.tmdbcompose.ui.screens.show_detail.TVShowDetailScreen
import com.jorgetargz.tmdbcompose.ui.screens.trending_movies.ListTrendingMoviesScreen
import com.jorgetargz.tmdbcompose.ui.screens.trending_shows.ListTrendingShowsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Constants.LIST_TRENDING_MOVIES_SCREEN
    ) {
        composable(Constants.LIST_TRENDING_MOVIES_SCREEN) {
            val navigateToMovieDetail: (Int) -> Unit = { movieId ->
                navController.navigate(Constants.MOVIE_DETAIL_SCREEN_ + movieId)
            }
            ListTrendingMoviesScreen(
                onNavigateToTvShows = { navController.navigate(Constants.LIST_TRENDING_SHOWS_SCREEN) },
                onNavigateToMovies = { navController.navigate(Constants.LIST_TRENDING_MOVIES_SCREEN) },
                onNavigateToMovieDetail = navigateToMovieDetail
            )
        }
        composable(Constants.LIST_TRENDING_SHOWS_SCREEN) {
            val navigateToTvShowDetail: (Int) -> Unit = { tvShowId ->
                navController.navigate(Constants.TV_SHOW_DETAIL_SCREEN + tvShowId)
            }
            ListTrendingShowsScreen(
                onNavigateToTvShows = { navController.navigate(Constants.LIST_TRENDING_SHOWS_SCREEN) },
                onNavigateToMovies = { navController.navigate(Constants.LIST_TRENDING_MOVIES_SCREEN) },
                onNavigateToTVShowDetail = navigateToTvShowDetail
            )
        }
        composable(Constants.MOVIE_DETAIL_SCREEN_MOVIE_ID) {
            val movieId = navController.currentBackStackEntry?.arguments?.getString(Constants.MOVIE_ID)
            DetailsMovieScreen(
                movieId = movieId?.toInt() ?: 0,
                onNavigateToTvShows = { navController.navigate(Constants.LIST_TRENDING_SHOWS_SCREEN) },
                onNavigateToMovies = { navController.navigate(Constants.LIST_TRENDING_MOVIES_SCREEN) }
            )
        }
        composable(Constants.MOVIE_DETAIL_SCREEN_SHOW_ID) {
            val tvShowId = navController.currentBackStackEntry?.arguments?.getString(Constants.SHOW_ID)
            TVShowDetailScreen(
                showId = tvShowId?.toInt() ?: 0,
                onNavigateToTvShows = { navController.navigate(Constants.LIST_TRENDING_SHOWS_SCREEN) },
                onNavigateToMovies = { navController.navigate(Constants.LIST_TRENDING_MOVIES_SCREEN) }
            )
        }
    }
}

