package com.tzel.movieflix.ui.dashboard.current.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.current.DashboardViewModel
import com.tzel.movieflix.ui.dashboard.current.composable.DashboardScreen
import kotlinx.serialization.Serializable

@Serializable
data object DashboardDestination : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = {
            launchSingleTop = true
            popUpTo(0)
        }
}

fun NavGraphBuilder.dashboardScreen(
    navigateTo: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit
) {
    composable<DashboardDestination> {
        val viewModel: DashboardViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        DashboardScreen(
            uiState = uiState,
            navigateTo = navigateTo,
            navigateBack = navigateBack
        )
    }
}