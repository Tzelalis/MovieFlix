package com.tzel.movieflix.ui.signin.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.signin.SignInViewModel
import com.tzel.movieflix.ui.signin.composable.SignInScreen
import kotlinx.serialization.Serializable

@Serializable
data object SignInDestination : NavigationDestination()

fun NavGraphBuilder.signInScreen(navigateBack: () -> Unit) {
    bottomSheet<SignInDestination> {
        val viewModel: SignInViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        SignInScreen(
            uiState = uiState,
            navigateBack = navigateBack
        )
    }
}