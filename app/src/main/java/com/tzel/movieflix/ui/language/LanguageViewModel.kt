package com.tzel.movieflix.ui.language

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.intl.Locale
import com.tzel.movieflix.domain.configuration.entity.Language
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.language.model.LanguageUiState
import com.tzel.movieflix.ui.movie.home.navigation.HomeDestination
import com.tzel.movieflix.usecase.configuration.GetAvailableLanguagesUseCase
import com.tzel.movieflix.usecase.configuration.GetSavedLanguageUseCase
import com.tzel.movieflix.usecase.configuration.SaveLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val getSavedLanguageUseCase: GetSavedLanguageUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<LanguageUiState>(LanguageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var availableLanguages: List<Language> = emptyList()

    init {
        loadAvailableLanguages()
    }

    private fun loadAvailableLanguages() {
        _uiState.update { LanguageUiState.Loading }

        launch {
            val textFieldState = TextFieldState()
            val savedLanguage = getSavedLanguageUseCase()

            val lang = Locale.current.language
            val languages = getAvailableLanguagesUseCase(lang)

            if (languages.isEmpty()) {
                _uiState.update { LanguageUiState.Error(::loadAvailableLanguages) }
                return@launch
            }

            availableLanguages = languages

            val initialLanguage = savedLanguage ?: languages.first()

            subscribeLanguageFiltering(textFieldState)

            _uiState.update {
                LanguageUiState.Success(
                    languages = languages,
                    initialSavedLanguage = initialLanguage,
                    selectedLanguage = mutableStateOf(initialLanguage),
                    textFieldState = textFieldState,
                    saveLanguage = ::saveLanguage,
                    updateSelectedLanguage = ::updateSelectedLanguage
                )
            }
        }
    }

    private fun updateSelectedLanguage(language: Language) {
        val state = uiState.value as? LanguageUiState.Success ?: return
        state.selectedLanguage.value = language
    }

    private fun subscribeLanguageFiltering(textFieldState: TextFieldState) {
        launch {
            snapshotFlow { textFieldState.text }.collectLatest { text ->
                val unFormattedText = text.toString().lowercase()
                val filteredList = availableLanguages.filter { lang ->
                    lang.englishName.lowercase().startsWith(unFormattedText) || lang.name?.lowercase()?.startsWith(unFormattedText) == true
                }
                _uiState.update {
                    (it as? LanguageUiState.Success)?.copy(languages = filteredList) ?: it
                }
            }
        }
    }

    private fun saveLanguage() {
        launch {
            val state = (uiState.value as? LanguageUiState.Success) ?: return@launch
            saveLanguageUseCase(state.selectedLanguage.value)

            _uiState.update { state.copy(navigateDestination = HomeDestination) }
        }
    }
}