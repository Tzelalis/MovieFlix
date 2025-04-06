package com.tzel.movieflix.ui.dashboard.more.dashboardsettings.model

sealed class DashboardSettingsUiState {
    data object Loading : DashboardSettingsUiState()

    data class Default(
        val items: List<DashboardSettingUiItem>,
        val onEvent: DashboardUiEvent
    ) : DashboardSettingsUiState()
}
