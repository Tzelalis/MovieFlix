package com.tzel.movieflix.ui.movie.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import coil3.compose.rememberAsyncImagePainter
import com.tzel.movieflix.R
import com.tzel.movieflix.utils.composable.modifier.noRippleClickable

@Composable
fun FavoriteIcon(
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val animatedColor by animateColorAsState(targetValue = color, label = "favorite_animation")

    Icon(
        modifier = modifier.noRippleClickable { onClick() },
        painter = rememberAsyncImagePainter(model = R.drawable.ic_heart),
        contentDescription = stringResource(id = R.string.home_favorite_content_description),
        tint = animatedColor
    )
}