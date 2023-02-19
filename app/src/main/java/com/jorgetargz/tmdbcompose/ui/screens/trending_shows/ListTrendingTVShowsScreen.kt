package com.jorgetargz.tmdbcompose.ui.screens.trending_shows

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
import com.jorgetargz.tmdbcompose.data.remote.network.Config
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.domain.models.TVShow
import com.jorgetargz.tmdbcompose.ui.common.CustomBottomAppBar
import com.jorgetargz.tmdbcompose.ui.common.CustomTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListTrendingShowsScreen(
    onNavigateToTvShows: () -> Unit,
    onNavigateToMovies: () -> Unit,
    onNavigateToTVShowDetail: (Int) -> Unit = { },
) {
    val state = rememberScaffoldState()
    Scaffold(
        scaffoldState = state,
        topBar = { CustomTopAppBar(stringResource(id = R.string.trending_shows)) },
        bottomBar = {
            CustomBottomAppBar(
                onNavigateToTvShows = { onNavigateToTvShows() },
                onNavigateToMovies = { onNavigateToMovies() }
            )
        },
        snackbarHost = { state.snackbarHostState }
    ) { padding ->
        ListTrendingShows(
            onNavigateToTVShowDetail = onNavigateToTVShowDetail,
            modifier = Modifier.padding(padding),
            snackbarHostState = state.snackbarHostState
        )
    }
}

@Composable
fun ListTrendingShows(
    onNavigateToTVShowDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ListTrendingTVShowsViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.handleEvent(ListTrendingTVShowsContract.ListTrendingTVShowsEvent.LoadTrendingTVShows)
    }
    if (state.value.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        ShowsList(
            modifier = modifier,
            shows = state.value.tvShows,
            onNavigateToTVShowDetail = onNavigateToTVShowDetail,
        )
    }
    LaunchedEffect(state.value.error) {
        state.value.error?.let { error ->
            val message = error
            snackbarHostState.showSnackbar(message)
        }
    }
}

@Composable
fun ShowsList(
    shows: List<TVShow>,
    onNavigateToTVShowDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues
    ) {
        items(shows.size) { index ->
            ShowItem(
                show = shows[index],
                onNavigateToTVShowDetail = onNavigateToTVShowDetail
            )
        }
    }
}

@Composable
fun ShowItem(
    show: TVShow,
    onNavigateToTVShowDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CardShow(
        show = show,
        modifier = modifier,
        onClick = { onNavigateToTVShowDetail(show.id) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardShow(
    show: TVShow,
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
            val posterURL = Config.IMAGE_URL + show.posterPath
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
                    text = show.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = show.overview,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                )
            }
        }
    }
}

