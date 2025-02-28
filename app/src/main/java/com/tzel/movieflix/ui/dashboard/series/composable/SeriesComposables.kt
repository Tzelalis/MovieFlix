package com.tzel.movieflix.ui.dashboard.series.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.series.model.SeriesUiEvent
import com.tzel.movieflix.ui.dashboard.series.model.SeriesUiState
import com.tzel.movieflix.ui.theme.MovieFlixTheme

@Composable
fun SeriesScreen(
    uiState: State<SeriesUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    SeriesContent(
        uiState = uiState,
        navigateTo = navigateTo
    )
}

@Composable
fun SeriesContent(
    uiState: State<SeriesUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {

}

@Preview
@Composable
private fun SeriesPreview() {
    val uiState = SeriesUiState(
        onEvent = SeriesUiEvent(
            onClick = {}
        )
    )

    MovieFlixTheme {
        SeriesScreen(
            uiState = remember { mutableStateOf(uiState) },
            navigateTo = {}
        )
    }
}