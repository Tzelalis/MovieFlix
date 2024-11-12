package com.tzel.movieflix.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = RedMedium,
    secondary = PurpleGrey80,
    onTertiary = GrayLight,
    background = Color.Black,
    onBackground = Color.White
)


@Composable
fun MovieFlixTheme(
    content: @Composable () -> Unit
) {
    AppRippleTheme {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}