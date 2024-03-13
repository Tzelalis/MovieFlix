package com.tzel.movieflix.ui.app.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.ui.app.navigation.AppNavHost

@Composable
fun AppScreen() {

    AppContent()
}

@Composable
private fun AppContent() {
    AppNavHost()
}

@Preview(showBackground = true)
@Composable
private fun AppPreview() {
    AppScreen()
}