package com.tzel.movieflix.ui.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = RedMedium,
    secondary = PurpleGrey80,
    onTertiary = GrayLight,
    background = Color.Black,
    onBackground = Color.White

    /* Other default colors to override
   background = Color(0xFFFFFBFE),
   surface = Color(0xFFFFFBFE),
   onPrimary = Color.White,
   onSecondary = Color.White,
   onTertiary = Color.White,
   onBackground = Color(0xFF1C1B1F),
   onSurface = Color(0xFF1C1B1F),
   */
)

private object RippleDarkTheme : RippleTheme {

    //Your custom implementation...
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            Color.White,
            lightTheme = true,
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            Color.Black,
            lightTheme = true
        )
}

@Composable
fun MovieFlixTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalRippleTheme provides RippleDarkTheme) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}