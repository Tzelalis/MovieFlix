package com.tzel.movieflix.ui.splash.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.splash.SplashViewModel
import com.tzel.movieflix.ui.splash.composable.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination : NavigationDestination()

fun NavGraphBuilder.splashScreen(navigateTo: (NavigationDestination) -> Unit) {
    composable<SplashDestination> {
        val viewModel: SplashViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SplashScreen(
            uiState = uiState,
            navigateTo = navigateTo
        )
    }
}