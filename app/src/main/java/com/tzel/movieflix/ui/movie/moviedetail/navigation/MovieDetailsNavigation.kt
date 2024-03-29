package com.tzel.movieflix.ui.movie.moviedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.safeNavigate
import com.tzel.movieflix.ui.movie.moviedetail.MovieDetailsViewModel
import com.tzel.movieflix.ui.movie.moviedetail.composable.MovieDetailsScreen


private const val MovieDetailsRoute = "movie_details"
const val MovieDetailsIdArgument = "movie_id"

fun NavGraphBuilder.movieDetailsScreen(
    navigateToMovieDetails: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable("$MovieDetailsRoute/{$MovieDetailsIdArgument}") {
        val viewModel: MovieDetailsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MovieDetailsScreen(
            uiState = uiState,
            navigateToMovie = navigateToMovieDetails,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToMovieDetails(movieId: String) {
    this.safeNavigate(
        route = "$MovieDetailsRoute/$movieId",
        launchSingleTop = false
    )
}