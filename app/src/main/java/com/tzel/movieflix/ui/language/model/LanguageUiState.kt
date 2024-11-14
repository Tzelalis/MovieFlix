package com.tzel.movieflix.ui.language.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.MutableState
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.ui.core.navigation.NavigationDestination

sealed class LanguageUiState {
    data object Loading : LanguageUiState()
    data class Error(val onRefresh: () -> Unit) : LanguageUiState()
    data class Success(
        val languages: List<Language>,
        val initialSavedLanguage: Language,
        val selectedLanguage: MutableState<Language>,
        val textFieldState: TextFieldState = TextFieldState(),
        val navigateDestination: NavigationDestination? = null,
        val saveLanguage: () -> Unit,
        val updateSelectedLanguage: (Language) -> Unit
    ) : LanguageUiState()
}
