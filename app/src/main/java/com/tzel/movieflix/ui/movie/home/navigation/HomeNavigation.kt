
package com.tzel.movieflix.ui.movie.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.safeNavigate
import com.tzel.movieflix.ui.movie.home.HomeViewModel
import com.tzel.movieflix.ui.movie.home.composable.HomeScreen


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

fun NavController.navigateToHome(popUpToRoute: String? = null) {
    this.safeNavigate(HomeRoute, popUpToRoute = popUpToRoute, inclusive = true)
}