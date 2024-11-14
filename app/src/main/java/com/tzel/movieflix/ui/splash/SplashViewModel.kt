package com.tzel.movieflix.ui.splash

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.language.navigation.LanguageDestination
import com.tzel.movieflix.ui.movie.home.navigation.HomeDestination
import com.tzel.movieflix.ui.splash.model.SplashUiState
import com.tzel.movieflix.usecase.configuration.GetSavedLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getSavedLanguageUseCase: GetSavedLanguageUseCase) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState = _uiState.asStateFlow()

    init {
        launch {
            delay(650) //dummy delay to show splash screen (request should replace it)
            val destination = if (getSavedLanguageUseCase() == null) LanguageDestination(hasBack = false, dropBackStack = true) else HomeDestination
            _uiState.update { it.copy(navigate = destination) }
        }
    }
}