package com.tzel.movieflix.ui.splash

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.splash.model.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            _uiState.update { it.copy(navigateToHome = true) }
        }
    }
}