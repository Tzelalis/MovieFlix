package com.tzel.movieflix.ui.dashboard.more.dashboardsettings.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.DashboardSettingsViewModel
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.composable.DashboardSettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object DashboardSettingsDestination : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = { launchSingleTop = false }
}

fun NavGraphBuilder.dashboardSettingsScreen() {
    composable<DashboardSettingsDestination> {
        val viewModel: DashboardSettingsViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        DashboardSettingsScreen(
            uiState = uiState
        )
    }
}