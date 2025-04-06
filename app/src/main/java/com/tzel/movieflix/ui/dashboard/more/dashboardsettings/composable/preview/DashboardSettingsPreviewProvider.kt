package com.tzel.movieflix.ui.dashboard.more.dashboardsettings.composable.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.model.DashboardSettingsUiState
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.model.DashboardUiEvent

class DashboardSettingsPreviewProvider : PreviewParameterProvider<DashboardSettingsUiState> {
    override val values = sequenceOf(
        DashboardSettingsUiState.Loading,
        DashboardSettingsUiState.Default(
            items = emptyList(),
            onEvent = DashboardUiEvent(onSave = {})
        )
    )
}