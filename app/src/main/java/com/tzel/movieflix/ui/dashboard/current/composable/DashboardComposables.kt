package com.tzel.movieflix.ui.dashboard.current.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.current.model.DashboardUiEvent
import com.tzel.movieflix.ui.dashboard.current.model.DashboardUiState
import com.tzel.movieflix.ui.dashboard.more.navigation.moreScreen
import com.tzel.movieflix.ui.dashboard.movie.home.navigation.MoviesDestination
import com.tzel.movieflix.ui.dashboard.movie.home.navigation.moviesScreen
import com.tzel.movieflix.ui.dashboard.series.navigation.seriesScreen
import com.tzel.movieflix.ui.theme.MovieFlixTheme

@Composable
fun DashboardScreen(
    uiState: State<DashboardUiState>,
    navigateTo: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit
) {
    DashboardContent(
        uiState = uiState,
        navigateTo = navigateTo,
        navigateBack = navigateBack
    )
}

@Composable
private fun DashboardContent(
    uiState: State<DashboardUiState>,
    navigateTo: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            navController = navController,
            startDestination = MoviesDestination
        ) {
            moviesScreen(navigateTo = navigateTo)

            seriesScreen(navigateTo = navigateTo)

            moreScreen(navigateTo = navigateTo)
        }

        MFNavigationBar(
            modifier = Modifier.fillMaxWidth(),
            items = uiState.value.navigationItems,
            currentDestination = { currentDestination },
            onItemClick = { item ->
                navController.navigate(item.destination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Preview
@Composable
private fun DashboardPreview() {
    val uiState = DashboardUiState(
        onEvent = DashboardUiEvent(onNavigationClick = {})
    )

    MovieFlixTheme {
        DashboardScreen(
            uiState = remember { mutableStateOf(uiState) },
            navigateTo = {},
            navigateBack = {}
        )
    }
}