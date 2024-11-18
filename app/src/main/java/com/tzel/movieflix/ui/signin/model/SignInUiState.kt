package com.tzel.movieflix.ui.signin.model

import com.tzel.movieflix.ui.core.navigation.NavigationDestination

sealed class SignInUiState() {
    data object Error : SignInUiState()
    data object SuccessSignIn : SignInUiState()
    data class Idle(
        val createSession: () -> Unit,
        val token: String? = null,
        val navigate: NavigationDestination? = null,
    ): SignInUiState() {
        val permissionUrl: String?
            get() = "https://www.themoviedb.org/auth/access?request_token=$token".takeUnless { token == null }
    }
}
