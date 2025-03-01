package com.tzel.movieflix.ui.splash.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.splash.model.SplashUiState
import com.tzel.movieflix.ui.theme.RedDark
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@Composable
fun SplashScreen(
    uiState: State<SplashUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    SplashSideEffects(
        destination = { uiState.value.navigate },
        navigateTo = navigateTo
    )

    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LogoText()
        PoweredByText(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun LogoText() {
    val isScaled = remember {
        mutableStateOf(false)
    }
    val animationFraction by animateFloatAsState(
        targetValue = if (isScaled.value) 2f else 0f,
        animationSpec = tween(
            durationMillis = 700,
            easing = LinearEasing,
        ),
        label = "splash_animation"
    )

    LaunchedEffect(key1 = Unit) {
        isScaled.value = true
    }

    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(Spacing_32dp)
            .graphicsLayer {
                val scale = animationFraction.coerceAtLeast(0.4f)

                scaleX = scale
                scaleY = scale
                alpha = animationFraction
            },
        text = stringResource(id = R.string.flix_movie),
        maxLines = 1,
        style = MaterialTheme.typography.headlineLarge,
        color = RedDark
    )
}

@Composable
private fun PoweredByText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .navigationBarsPadding()
            .padding(bottom = Spacing_8dp),
        text = stringResource(id = R.string.splash_powered_by),
        style = MaterialTheme.typography.labelMedium,
    )
}

@Composable
private fun SplashSideEffects(
    destination: () -> NavigationDestination?,
    navigateTo: (NavigationDestination) -> Unit
) {
    LaunchedEffect(key1 = destination()) {
        destination()?.let { destination ->
            navigateTo(destination)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    val uiState = remember {
        mutableStateOf(
            SplashUiState(navigate = null)
        )
    }

    SplashScreen(
        uiState = uiState,
        navigateTo = {}
    )
}