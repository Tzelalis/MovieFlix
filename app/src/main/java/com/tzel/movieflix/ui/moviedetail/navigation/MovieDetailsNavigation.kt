package com.tzel.movieflix.ui.moviedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.safeNavigate
import com.tzel.movieflix.ui.moviedetail.MovieDetailsViewModel
import com.tzel.movieflix.ui.moviedetail.composable.MovieDetailsScreen


private const val MovieDetailsRoute = "movie_details"
const val MovieDetailsIdArgument = "movie_id"

fun NavGraphBuilder.movieDetailsScreen() {
    composable("$MovieDetailsRoute/{$MovieDetailsIdArgument}") {
        val viewModel: MovieDetailsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MovieDetailsScreen(uiState = uiState)
    }
}

fun NavController.navigateToMovieDetails(movieId: String) {
    this.safeNavigate("$MovieDetailsRoute/$movieId")
}