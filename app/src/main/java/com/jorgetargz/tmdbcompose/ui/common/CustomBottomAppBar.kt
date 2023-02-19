package com.jorgetargz.tmdbcompose.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jorgetargz.tmdbcompose.R

@Composable
fun CustomBottomAppBar(
    onNavigateToTvShows: () -> Unit,
    onNavigateToMovies: () -> Unit,
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomNavButton(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50)),
                onClick = { onNavigateToTvShows() },
                text = stringResource(id = R.string.tv_shows)
            )
            CustomNavButton(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50)),
                onClick = { onNavigateToMovies() },
                text = stringResource(id = R.string.movies)
            )
        }
    }
}

@Composable
fun CustomNavButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary
        )
    }
}