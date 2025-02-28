package com.tzel.movieflix.ui.dashboard.series.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.series.SeriesViewModel
import com.tzel.movieflix.ui.dashboard.series.composable.SeriesScreen
import kotlinx.serialization.Serializable

@Serializable
data object SeriesDestination : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = { launchSingleTop = false }
}

fun NavGraphBuilder.seriesScreen(navigateTo: (NavigationDestination) -> Unit) {
    composable<SeriesDestination> {
        val viewModel: SeriesViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SeriesScreen(
            uiState = uiState,
            navigateTo = navigateTo,
        )
    }
}