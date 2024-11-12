
package com.tzel.movieflix.ui.movie.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.movie.home.HomeViewModel
import com.tzel.movieflix.ui.movie.home.composable.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeDestination: NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = {
            launchSingleTop = true
            popUpTo(0)
        }
}

fun NavGraphBuilder.homeScreen(
    navigateToMovieDetails: (id: String) -> Unit
) {
    composable<HomeDestination> {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            uiState = uiState,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }
}