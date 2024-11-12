package com.tzel.movieflix.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface Sizes {
    val extraSmall: Dp
    val small: Dp
    val medium: Dp
    val large: Dp
    val extraLarge: Dp

    object NavigationBars : Sizes {
        override val extraSmall = 32.dp
        override val small = 48.dp
        override val medium = 64.dp
        override val large = 96.dp
        override val extraLarge = 132.dp
    }

    object Icons : Sizes {
        override val extraSmall = 16.dp
        override val small = 24.dp
        override val medium = 32.dp
        override val large = 64.dp
        override val extraLarge = 128.dp
    }
}