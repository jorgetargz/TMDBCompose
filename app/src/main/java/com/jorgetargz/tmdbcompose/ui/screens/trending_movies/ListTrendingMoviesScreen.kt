package com.jorgetargz.tmdbcompose.ui.screens.trending_movies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jorgetargz.tmdbcompose.R
import com.jorgetargz.tmdbcompose.data.remote.network.common.Constantes
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.ui.common.CustomBottomAppBar
import com.jorgetargz.tmdbcompose.ui.common.CustomSnackbar
import com.jorgetargz.tmdbcompose.ui.common.CustomTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListTrendingMoviesScreen(
    onNavigateToTvShows: () -> Unit,
    onNavigateToMovies: () -> Unit,
    onNavigateToMovieDetail: (Int) -> Unit,
) {
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        topBar = { CustomTopAppBar(stringResource(id = R.string.trending_movies)) },
        bottomBar = {
            CustomBottomAppBar(
                onNavigateToTvShows = { onNavigateToTvShows() },
                onNavigateToMovies = { onNavigateToMovies() }
            )
        },
        snackbarHost = { state.snackbarHostState }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ListTrendingMovies(
                onNavigateToMovieDetail = onNavigateToMovieDetail,
                snackbarHostState = state.snackbarHostState
            )
            CustomSnackbar(
                snackbarHostState = state.snackbarHostState,
                onDismiss = { state.snackbarHostState.currentSnackbarData?.dismiss() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun ListTrendingMovies(
    onNavigateToMovieDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ListTrendingMoviesViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(ListTrendingMoviesContract.ListTrendingMoviesEvent.LoadTrendingMovies)
    }

    val dismiss = stringResource(id = R.string.dismiss)
    LaunchedEffect(state.value.error) {
        state.value.error?.let { error ->
            snackbarHostState.showSnackbar(error, dismiss)
            snackbarHostState.currentSnackbarData?.dismiss()
            viewModel.handleEvent(ListTrendingMoviesContract.ListTrendingMoviesEvent.ClearError)
        }
    }

    if (state.value.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        MoviesList(
            modifier = modifier,
            movies = state.value.movies,
            onNavigateToMovieDetail = onNavigateToMovieDetail,
        )
    }
}

@Composable
fun MoviesList(
    movies: List<Movie>,
    onNavigateToMovieDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues
    ) {
        items(movies.size) { index ->
            MovieItem(
                movie = movies[index],
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onNavigateToMovieDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CardMovie(
        movie = movie,
        modifier = modifier,
        onClick = { onNavigateToMovieDetail(movie.id) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardMovie(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val posterURL = Constantes.IMAGE_URL + movie.posterPath
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterURL)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
            }
        }
    }
}

