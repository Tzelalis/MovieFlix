package com.tzel.movieflix.ui.core.composable

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.PlatformTextStyle
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_8dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MVButton(
    modifier: Modifier = Modifier,
    text: String,
    leadingIcon: Painter? = null,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalRippleConfiguration provides RippleConfiguration(color = Color.Black, rippleAlpha = null)) {
        Button(
            modifier = modifier,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            contentPadding = PaddingValues(Spacing_8dp),
            onClick = onClick
        ) {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(Sizes.Icons.small),
                    painter = leadingIcon,
                    contentDescription = "Play Trailer",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}