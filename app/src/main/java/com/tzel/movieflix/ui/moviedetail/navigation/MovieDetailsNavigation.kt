package com.tzel.movieflix.ui.moviedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.moviedetail.MovieDetailsViewModel
import com.tzel.movieflix.ui.moviedetail.composable.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDestination(val id: String) : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = { launchSingleTop = false }
}

fun NavGraphBuilder.movieDetailsScreen(
    navigateToMovieDetails: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable<MovieDetailsDestination> {
        val viewModel: MovieDetailsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MovieDetailsScreen(
            uiState = uiState,
            navigateToMovie = navigateToMovieDetails,
            onBackClick = onBackClick
        )
    }
}