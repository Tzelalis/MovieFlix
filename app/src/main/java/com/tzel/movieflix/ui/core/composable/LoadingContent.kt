package com.tzel.movieflix.ui.core.composable

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.theme.Spacing_32dp
import gr.opap.utils.composable.modifier.placeholder.PlaceholderHighlight
import gr.opap.utils.composable.modifier.placeholder.shimmer

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .aspectRatio(1f),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(Spacing_32dp))
        Text(text = stringResource(id = R.string.generic_loading))
    }
}

val genericPlaceholderHighlight = PlaceholderHighlight.shimmer(
    highlightColor = Color.White,
    animationSpec = infiniteRepeatable(animation = tween(durationMillis = 1500, delayMillis = 200), repeatMode = RepeatMode.Restart),
)