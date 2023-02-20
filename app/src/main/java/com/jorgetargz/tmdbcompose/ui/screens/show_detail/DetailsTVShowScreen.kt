package com.jorgetargz.tmdbcompose.ui.screens.show_detail

import androidx.compose.foundation.layout.*
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
import com.jorgetargz.tmdbcompose.domain.models.TVShow
import com.jorgetargz.tmdbcompose.ui.common.CustomBottomAppBar
import com.jorgetargz.tmdbcompose.ui.common.CustomSnackbar
import com.jorgetargz.tmdbcompose.ui.common.CustomTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun TVShowDetailScreen(
    showId: Int,
    onNavigateToTvShows: () -> Unit,
    onNavigateToMovies: () -> Unit,
) {
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        topBar = { CustomTopAppBar(stringResource(id = R.string.show_detail)) },
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
            MovieDetail(
                showId = showId,
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
fun MovieDetail(
    showId: Int,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: DetailsTVShowViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(DetailsTVShowContract.DetailsTVShowEvent.LoadTVShow(showId))
    }

    val dismiss = stringResource(id = R.string.dismiss)
    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            snackbarHostState.showSnackbar(it, dismiss, SnackbarDuration.Long)
            snackbarHostState.currentSnackbarData?.dismiss()
            viewModel.handleEvent(DetailsTVShowContract.DetailsTVShowEvent.ClearError)
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
        MovieDetailCard(
            modifier = modifier,
            show = state.value.tvShow,
        )
    }
}

@Composable
fun MovieDetailCard(
    modifier: Modifier = Modifier,
    show: TVShow,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val posterURL = Constantes.IMAGE_URL + show.posterPath
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(posterURL)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = show.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = show.overview,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = show.firstAirDate.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row {
            Text(
                text = stringResource(id = R.string.vote_average),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = show.voteAverage.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.vote_count),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = show.voteCount.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}