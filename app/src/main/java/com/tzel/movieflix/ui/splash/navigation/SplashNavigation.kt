package com.tzel.movieflix.ui.splash.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.splash.SplashViewModel
import com.tzel.movieflix.ui.splash.composable.SplashScreen


const val SplashRoute = "splash"

fun NavGraphBuilder.splashScreen(navigateToDashboard: () -> Unit) {
    composable(SplashRoute) {
        val viewModel: SplashViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SplashScreen(
            uiState = uiState,
            navigateToHome = navigateToDashboard
        )
    }
}