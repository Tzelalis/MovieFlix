package com.tzel.movieflix.utils.composable.image

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest

@Composable
internal fun rememberImageRequester(
    context: Context = LocalContext.current,
): ImageRequest.Builder {
    return remember {
        ImageRequest.Builder(context)
    }
}