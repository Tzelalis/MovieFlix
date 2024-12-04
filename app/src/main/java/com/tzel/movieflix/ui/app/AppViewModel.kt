package com.tzel.movieflix.ui.app

import com.tzel.movieflix.domain.auth.NoSessionIdFound
import com.tzel.movieflix.ui.app.model.AppUiState
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.signin.navigation.SignInDestination
import com.tzel.movieflix.usecase.auth.ClearAuthExceptionsUseCase
import com.tzel.movieflix.usecase.auth.GetAuthExceptionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val getAuthExceptionsUseCase: GetAuthExceptionsUseCase,
    private val clearAuthExceptionsUseCase: ClearAuthExceptionsUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        AppUiState(
            clearDestination = ::clearAuthExceptions
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        subscribeToAuthExceptions()
    }

    private fun subscribeToAuthExceptions() {
        launch {
            getAuthExceptionsUseCase().collectLatest { exception ->
                exception?.let { authException ->
                    _uiState.update {
                        when (authException) {
                            is NoSessionIdFound -> {
                                it.copy(navigateDestination = SignInDestination)
                            }

                            else -> it
                        }
                    }
                }
            }
        }
    }

    private fun clearAuthExceptions() {
        launch {
            clearAuthExceptionsUseCase()
            _uiState.update { it.copy(navigateDestination = null) }
        }
    }
}