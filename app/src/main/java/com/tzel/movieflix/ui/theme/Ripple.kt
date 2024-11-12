package com.tzel.movieflix.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppRippleTheme(content: @Composable () -> Unit) {
    val opapCyRippleConfiguration = RippleConfiguration(color = Color.White, rippleAlpha = null)

    CompositionLocalProvider(
        value = LocalRippleConfiguration provides opapCyRippleConfiguration,
        content = content
    )
}