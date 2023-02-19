package com.jorgetargz.tmdbcompose.ui.common

import android.content.Intent
import android.net.Uri
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.content.ContextCompat.startActivity
import com.jorgetargz.tmdbcompose.R

@Composable
fun CustomTopAppBar(
    title: String
) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(title) },
        actions = {
            AppBarAction(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_github),
                onClick = {
                    val myProfileURI = Uri.parse(Constants.GITHUB_PROFILE_URL)
                    val intent = Intent(Intent.ACTION_VIEW, myProfileURI)
                    startActivity(context, intent, null)
                }
            )
        }
    )
}

@Composable
private fun AppBarAction(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}