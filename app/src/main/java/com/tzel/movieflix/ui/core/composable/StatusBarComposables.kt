package com.tzel.movieflix.ui.core.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import kotlin.math.abs

@Composable
fun StatusBarBackground(
    modifier: Modifier = Modifier,
    state: LazyListState,
    color: Color = Color.Black,
    maxFloat: Float = 0.7f
) {
    val statusBarAlpha by remember {
        derivedStateOf {
            if (state.layoutInfo.visibleItemsInfo.isEmpty() || state.layoutInfo.visibleItemsInfo[0].offset == 0) {
                0f
            } else if (state.firstVisibleItemIndex == 0) {
                abs((state.firstVisibleItemScrollOffset / state.layoutInfo.visibleItemsInfo[0].size.toFloat()) * 0.7f).coerceIn(0f, 1f)
            } else 0.7f
        }
    }

    StatusBarBackground(
        modifier = modifier,
        alpha = { statusBarAlpha },
        color = color,
        maxFloat = maxFloat
    )
}

@Composable
fun StatusBarBackground(
    modifier: Modifier = Modifier,
    state: ScrollState,
    color: Color = Color.Black,
    maxFloat: Float = 0.7f
) {
    val statusBarAlpha by remember {
        derivedStateOf {
            if (state.value == 0) {
                0f
            } else {
                state.value * 0.7f.coerceIn(0f, 1f)
            }
        }
    }

    StatusBarBackground(
        modifier = modifier,
        alpha = { statusBarAlpha },
        color = color,
        maxFloat = maxFloat
    )
}

@Composable
fun StatusBarBackground(
    modifier: Modifier = Modifier,
    alpha: () -> Float,
    color: Color = Color.Red,
    maxFloat: Float = 0.7f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind { drawRect(color = color.copy(alpha = alpha().coerceIn(0f, maxFloat))) }
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
    )
}