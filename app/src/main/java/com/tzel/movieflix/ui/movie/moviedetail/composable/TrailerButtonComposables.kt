package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrailerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleConfiguration provides RippleConfiguration(color = Color.Black, rippleAlpha = null)) {
        Button(
            modifier = modifier.padding(Spacing_16dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            contentPadding = PaddingValues(Spacing_8dp),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(Sizes.Icons.small),
                painter = painterResource(id = R.drawable.ic_play_arrow),
                contentDescription = "Play Trailer",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(R.string.home_details_watch_trailer_button),
                style = MaterialTheme.typography.bodyLarge.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}