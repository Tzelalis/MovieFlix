package com.tzel.movieflix.ui.dashboard.more.current.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.more.current.MoreViewModel
import com.tzel.movieflix.ui.dashboard.more.current.composable.MoreScreen
import kotlinx.serialization.Serializable

@Serializable
data object MoreDestination : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = { launchSingleTop = false }
}

fun NavGraphBuilder.moreScreen(navigateTo: (NavigationDestination) -> Unit) {
    composable<MoreDestination> {
        val viewModel: MoreViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        MoreScreen(
            uiState = uiState,
            navigateTo = navigateTo,
        )
    }
}