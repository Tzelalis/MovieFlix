package com.tzel.movieflix.ui.language.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.language.LanguageViewModel
import com.tzel.movieflix.ui.language.composable.LanguageScreen
import kotlinx.serialization.Serializable

@Serializable
data class LanguageDestination(
    val hasBack: Boolean,
    private val dropBackStack: Boolean
) : NavigationDestination() {
    override val builder: NavOptionsBuilder.() -> Unit
        get() = { if (dropBackStack) popUpTo(0) }
}

fun NavGraphBuilder.languageScreen(
    navigateTo: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit
) {
    composable<LanguageDestination> {
        val viewModel: LanguageViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val args = it.toRoute<LanguageDestination>()

        LanguageScreen(
            uiState = uiState,
            hasBack = args.hasBack,
            navigateTo = navigateTo,
            navigateBack = navigateBack
        )
    }
}