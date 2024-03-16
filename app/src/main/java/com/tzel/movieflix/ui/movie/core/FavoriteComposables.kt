package com.tzel.movieflix.ui.movie.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tzel.movieflix.R

@Composable
fun FavoriteIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedColor by animateColorAsState(targetValue = color, label = "favorite_animation")

    Icon(
        modifier = modifier.size(18.dp),
        painter = rememberAsyncImagePainter(model = R.drawable.ic_heart),
        contentDescription = stringResource(id = R.string.home_favorite_content_description),
        tint = animatedColor
    )
}