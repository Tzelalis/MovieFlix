package com.tzel.movieflix.ui.home.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.ui.home.model.HomeUiState

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateToMovieDetails: (id: String) -> Unit
) {

    HomeContent(
        uiState = uiState,
        navigateToMovieDetails = navigateToMovieDetails
    )
}

@Composable
private fun HomeContent(
    uiState: State<HomeUiState>,
    navigateToMovieDetails: (id: String) -> Unit
) {
}

@Preview
@Composable
private fun HomePreview() {
    val uiState = remember {
        mutableStateOf(HomeUiState())
    }

    HomeScreen(
        uiState = uiState,
        navigateToMovieDetails = {}
    )
}