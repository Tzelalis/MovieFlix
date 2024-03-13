package com.tzel.movieflix.utils.composable.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer

@Stable
fun Modifier.rotate(degrees: () -> Float) = graphicsLayer { rotationZ = degrees() }

@Stable
fun Modifier.alpha(alpha: () -> Float) = graphicsLayer {
    this.alpha = alpha()
}

@Stable
fun Modifier.clip(shape: () -> Shape) = graphicsLayer {
    this.shape = shape()
    this.clip = true
}

@Stable
fun Modifier.scale(percentage: () -> Float) = graphicsLayer {
    this.scaleX = percentage()
    this.scaleY = percentage()
}