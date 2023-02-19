package com.jorgetargz.tmdbcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jorgetargz.tmdbcompose.ui.screens.movie_detail.DetailsMovieScreen
import com.jorgetargz.tmdbcompose.ui.screens.trending_movies.ListTrendingMoviesScreen
import com.jorgetargz.tmdbcompose.ui.screens.trending_shows.ListTrendingShowsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "ListTrendingMoviesScreen"
    ) {
        composable("ListTrendingMoviesScreen") {
            val navigateToMovieDetail: (Int) -> Unit = { movieId ->
                navController.navigate("MovieDetailScreen/$movieId")
            }
            ListTrendingMoviesScreen(
                onNavigateToTvShows = { navController.navigate("ListTrendingShowsScreen") },
                onNavigateToMovies = { navController.navigate("ListTrendingMoviesScreen") },
                onNavigateToMovieDetail = navigateToMovieDetail
            )
        }
        composable("ListTrendingShowsScreen") {
            val navigateToTvShowDetail: (Int) -> Unit = { tvShowId ->
                navController.navigate("TVShowDetailScreen/$tvShowId")
            }
            ListTrendingShowsScreen(
                onNavigateToTvShows = { navController.navigate("ListTrendingShowsScreen") },
                onNavigateToMovies = { navController.navigate("ListTrendingMoviesScreen") },
                onNavigateToTVShowDetail = navigateToTvShowDetail
            )
        }
        composable("MovieDetailScreen/{movieId}") {
            val movieId = navController.currentBackStackEntry?.arguments?.getString("movieId")
            DetailsMovieScreen(
                movieId = movieId?.toInt() ?: 0,
                onNavigateToTvShows = { navController.navigate("ListTrendingShowsScreen") },
                onNavigateToMovies = { navController.navigate("ListTrendingMoviesScreen") }
            )
        }
        composable("TVShowDetailScreen/{tvShowId}") {

        }
    }
}

