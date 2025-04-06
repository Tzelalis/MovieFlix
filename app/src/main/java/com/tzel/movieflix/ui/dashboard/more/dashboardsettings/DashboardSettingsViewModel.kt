package com.tzel.movieflix.ui.dashboard.more.dashboardsettings

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.model.DashboardSettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardSettingsViewModel @Inject constructor() : BaseViewModel() {
    private val _uiState: MutableStateFlow<DashboardSettingsUiState> = MutableStateFlow(DashboardSettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()


}