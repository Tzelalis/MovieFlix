package com.tzel.movieflix.ui.dashboard.series

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.dashboard.series.model.SeriesUiEvent
import com.tzel.movieflix.ui.dashboard.series.model.SeriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor() : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        SeriesUiState(
            onEvent = SeriesUiEvent(
                onClick = {}
            )
        )
    )
    val uiState = _uiState.asStateFlow()
}