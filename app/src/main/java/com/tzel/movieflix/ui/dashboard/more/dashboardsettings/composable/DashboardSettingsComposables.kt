package com.tzel.movieflix.ui.dashboard.more.dashboardsettings.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.composable.preview.DashboardSettingsPreviewProvider
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.model.DashboardSettingsUiState

@Composable
fun DashboardSettingsScreen(uiState: State<DashboardSettingsUiState>) {

    DashboardSettingsContent(uiState)
}

@Composable
private fun DashboardSettingsContent(uiState: State<DashboardSettingsUiState>) {
}

@Preview
@Composable
private fun DashboardSettingsPreview(@PreviewParameter(DashboardSettingsPreviewProvider::class) uiState: DashboardSettingsUiState) {
    DashboardSettingsContent(uiState = remember { mutableStateOf(uiState) })
}