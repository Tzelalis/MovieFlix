package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchlistUiState
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_8dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToWatchlistComposables(
    modifier: Modifier = Modifier,
    state: State<WatchlistUiState>,
    text: String,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleConfiguration provides RippleConfiguration(color = Color.Black, rippleAlpha = null)) {
        Button(
            modifier = modifier,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.surface
            ),
            contentPadding = PaddingValues(Spacing_8dp),
            onClick = onClick
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = Spacing_8dp)
                    .size(Sizes.Icons.small),
                contentAlignment = Alignment.Center,
            ) {
                state.value.iconRes?.let { iconRes ->
                    Icon(
                        modifier = Modifier.size(Sizes.Icons.small),
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface

                    )
                }

                if (state.value is WatchlistUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(Sizes.Icons.small),
                        color = MaterialTheme.colorScheme.surface,
                        strokeWidth = 1.dp
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
            )
        }
    }
}