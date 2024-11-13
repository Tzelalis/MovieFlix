package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.alpha

@Composable
fun TrendingBackground(
    imageUrl: String?,
    alpha: () -> Float,
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean = { true },
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
) {
    imageUrl?.let { image ->
        if (isVisible()) {
            AsyncImage(
                modifier = modifier
                    .fillMaxSize()
                    .blur(10.dp)
                    .alpha { alpha() },
                model = imageRequester.data(image).build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}