package com.tzel.movieflix.ui.signin

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.signin.model.SignInUiState
import com.tzel.movieflix.usecase.auth.CreateSessionUseCase
import com.tzel.movieflix.usecase.auth.RequestTemporaryRequestTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getTemporaryRequestTokenUseCase: RequestTemporaryRequestTokenUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<SignInUiState>(
        SignInUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        requestTemporaryToken()
    }

    private fun requestTemporaryToken() {
        launch {
            val tempToken = getTemporaryRequestTokenUseCase() ?: return@launch
            _uiState.update {
                SignInUiState.Idle(
                    token = tempToken,
                    navigate = null,
                    createSession = ::createSession,
                )
            }
        }
    }

    private fun createSession() {
        val state = uiState.value as? SignInUiState.Idle ?: return

        launch {
            state.token?.let { token ->
                val successfulSignIn = createSessionUseCase(token)

                if (successfulSignIn) {
                    _uiState.update { SignInUiState.SuccessSignIn }
                } else {
                    _uiState.update { SignInUiState.Error }
                }
            }
        }
    }
}