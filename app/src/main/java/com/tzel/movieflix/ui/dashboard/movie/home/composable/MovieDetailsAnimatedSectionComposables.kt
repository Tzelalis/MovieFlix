package com.tzel.movieflix.ui.dashboard.movie.home.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable

@Composable
fun MovieDetailsAnimatedSection(
    visible: () -> Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible(),
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
        content = { content() }
    )
}