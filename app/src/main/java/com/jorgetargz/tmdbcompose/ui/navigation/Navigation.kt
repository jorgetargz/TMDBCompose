package com.jorgetargz.tmdbcompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            ListTrendingMoviesScreen(
                { navController.navigate("ListTrendingShowsScreen") },
                { navController.navigate("ListTrendingMoviesScreen") }
            )
        }
        composable("ListTrendingShowsScreen") {
            ListTrendingShowsScreen(
                { navController.navigate("ListTrendingShowsScreen") },
                { navController.navigate("ListTrendingMoviesScreen") }
            )
        }

    }
}

