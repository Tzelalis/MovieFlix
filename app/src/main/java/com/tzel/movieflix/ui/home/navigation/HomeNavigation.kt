
package com.tzel.movieflix.ui.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.safeNavigate
import com.tzel.movieflix.ui.home.HomeViewModel
import com.tzel.movieflix.ui.home.composable.HomeScreen


private const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
    navigateToMovieDetails: (id: String) -> Unit
) {
    composable(HomeRoute) {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            uiState = uiState,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}

fun NavController.navigateToHome() {
    this.safeNavigate(HomeRoute)
}