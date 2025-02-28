package com.tzel.movieflix.ui.dashboard.current

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.dashboard.current.model.DashboardUiEvent
import com.tzel.movieflix.ui.dashboard.current.model.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        DashboardUiState(
            onEvent = DashboardUiEvent(
                onNavigationClick = {}
            )
        )
    )
    val uiState = _uiState.asStateFlow()


}