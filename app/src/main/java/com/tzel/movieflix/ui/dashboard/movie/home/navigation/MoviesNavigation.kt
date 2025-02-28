package com.tzel.movieflix.ui.dashboard.movie.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.movie.home.MoviesViewModel
import com.tzel.movieflix.ui.dashboard.movie.home.composable.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object MoviesDestination : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = {
            launchSingleTop = true
            popUpTo(0)
        }
}

fun NavGraphBuilder.moviesScreen(
    navigateTo: (NavigationDestination) -> Unit
) {
    composable<MoviesDestination> {
        val viewModel: MoviesViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            uiState = uiState,
            navigateTo = navigateTo
        )
    }
}