package com.tzel.movieflix.ui.dashboard.movie.home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.size.Size
import coil3.toBitmap
import kotlinx.coroutines.launch

@Composable
fun TrendingBackground(
    imageUrl: String?,
    alpha: () -> Float,
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean = { true },
) {
    val containerColor = remember { mutableStateOf<Color?>(null) }
    val coroutineScope = rememberCoroutineScope()

    imageUrl?.let { url ->
        if (isVisible()) {
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .allowHardware(false)
                    .size(Size.ORIGINAL)
                    .listener(
                        onSuccess = { _, result ->
                            val bitmap = result.image.toBitmap()
                            coroutineScope.launch {
                                val palette = Palette.from(bitmap).generate()
                                containerColor.value = palette.mutedSwatch?.rgb?.let { Color(it) }
                            }
                        },
                    )
                    .build()
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .drawBehind {
                        containerColor.value?.let { color ->
                            val brush = Brush.verticalGradient(
                                0f to color.copy(alpha = alpha()),
                                1f to color.copy(alpha = 0.2f * alpha()),
                            )
                            drawRect(
                                brush = brush,
                                size = size,
                            )
                        }
                    }
            )
        }
    }
}