package com.jorgetargz.tmdbcompose.ui.screens.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jorgetargz.tmdbcompose.R
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.ui.common.CustomBottomAppBar
import com.jorgetargz.tmdbcompose.ui.common.CustomTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsMovieScreen(
    movieId: Int,
    onNavigateToTvShows: () -> Unit,
    onNavigateToMovies: () -> Unit,
) {
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        topBar = { CustomTopAppBar(stringResource(id = R.string.movie_detail)) },
        bottomBar = {
            CustomBottomAppBar(
                onNavigateToTvShows = { onNavigateToTvShows() },
                onNavigateToMovies = { onNavigateToMovies() }
            )
        },
        snackbarHost = { state.snackbarHostState }
    ) { padding ->
        MovieDetail(
            movieId = movieId,
            modifier = Modifier.padding(padding),
            snackbarHostState = state.snackbarHostState
        )
    }
}

@Composable
fun MovieDetail(
    movieId: Int,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: DetailsMovieViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleEvent(DetailsMovieContract.DetailsMovieEvent.LoadMovie(movieId))
    }
    if (state.value.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        MovieDetailCard(
            modifier = modifier,
            movie = state.value.movie,
        )
    }
    LaunchedEffect(state.value.error) {
        state.value.error?.let { error ->
            val message = error
            snackbarHostState.showSnackbar(message)
            viewModel.handleEvent(DetailsMovieContract.DetailsMovieEvent.ClearError)
        }
    }
}

@Composable
fun MovieDetailCard(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = movie.releaseDate.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = movie.voteAverage.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}