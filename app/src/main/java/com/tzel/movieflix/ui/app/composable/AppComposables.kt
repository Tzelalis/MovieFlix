package com.tzel.movieflix.ui.app.composable

import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.tzel.movieflix.ui.app.AppViewModel
import com.tzel.movieflix.ui.app.model.AppUiState
import com.tzel.movieflix.ui.app.navigation.AppNavHost

@Composable
fun AppScreen() {
    val viewModel = hiltViewModel<AppViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AppContent(uiState = uiState)
}

@Composable
private fun AppContent(uiState: State<AppUiState>) {
    val navigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(navigator)

    LaunchedEffect(navigator.navigatorSheetState.isVisible.not())  {
        if(navigator.navigatorSheetState.isVisible.not()){
            navController.navigateUp()
        }
    }

    LaunchedEffect(uiState.value.navigateDestination) {
        uiState.value.navigateDestination?.let { destination ->
            navController.navigate(destination)
        }
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = navigator,
        sheetShape = MaterialTheme.shapes.large,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        AppNavHost(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    AppScreen()
}