package com.tzel.movieflix.ui.dashboard.more.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.more.model.MoreUiEvent
import com.tzel.movieflix.ui.dashboard.more.model.MoreUiState
import com.tzel.movieflix.ui.theme.MovieFlixTheme

@Composable
fun MoreScreen(
    uiState: State<MoreUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    MoreContent(
        uiState = uiState,
        navigateTo = navigateTo
    )
}

@Composable
fun MoreContent(
    uiState: State<MoreUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {

}

@Preview
@Composable
private fun MorePreview() {
    val uiState = MoreUiState(
        onEvent = MoreUiEvent(
            onClick = {}
        )
    )

    MovieFlixTheme {
        MoreScreen(
            uiState = remember { mutableStateOf(uiState) },
            navigateTo = {}
        )
    }
}