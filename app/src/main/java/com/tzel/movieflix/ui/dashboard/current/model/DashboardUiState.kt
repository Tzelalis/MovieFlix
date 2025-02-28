package com.tzel.movieflix.ui.dashboard.current.model

data class DashboardUiState(
    val onEvent: DashboardUiEvent
) {
    val navigationItems: List<NavigationUiItem> = NavigationUiItem.items
}
