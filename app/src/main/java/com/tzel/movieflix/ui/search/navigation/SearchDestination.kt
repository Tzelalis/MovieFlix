package com.tzel.movieflix.ui.search.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.search.SearchViewModel
import com.tzel.movieflix.ui.search.composable.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchDestination : NavigationDestination()

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
    navigateTo: (NavigationDestination) -> Unit
) {
    composable<SearchDestination> {
        val viewModel: SearchViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SearchScreen(
            uiState = uiState,
            onBackClick = onBackClick,
            onMovieClick = navigateTo
        )
    }
}