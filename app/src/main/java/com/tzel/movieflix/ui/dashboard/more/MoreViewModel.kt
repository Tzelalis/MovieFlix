package com.tzel.movieflix.ui.dashboard.more

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.dashboard.more.model.MoreUiEvent
import com.tzel.movieflix.ui.dashboard.more.model.MoreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor() : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        MoreUiState(
            onEvent = MoreUiEvent(onClick = {})
        )
    )
    val uiState = _uiState.asStateFlow()

}