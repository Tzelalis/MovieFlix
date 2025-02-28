package com.tzel.movieflix.ui.language.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.ErrorContent
import com.tzel.movieflix.ui.core.composable.LoadingContent
import com.tzel.movieflix.ui.core.composable.MFTextField
import com.tzel.movieflix.ui.core.composable.MVButton
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.movie.core.Headline
import com.tzel.movieflix.ui.language.model.LanguageUiState
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@Composable
fun LanguageScreen(
    uiState: State<LanguageUiState>,
    hasBack: Boolean,
    navigateTo: (NavigationDestination) -> Unit,
    navigateBack: () -> Unit
) {
    LanguageSideEffects(
        uiState = uiState.value,
        navigateTo = navigateTo,
    )

    when (val state = uiState.value) {
        LanguageUiState.Loading -> LoadingContent()
        is LanguageUiState.Error -> ErrorContent { state.onRefresh() }
        is LanguageUiState.Success -> LanguageContent(
            uiState = state,
            hasBack = hasBack,
            navigateBack = navigateBack
        )
    }

}

@Composable
fun LanguageSideEffects(
    uiState: LanguageUiState,
    navigateTo: (NavigationDestination) -> Unit
) {
    LaunchedEffect(uiState) {
        val state = uiState as? LanguageUiState.Success ?: return@LaunchedEffect
        state.navigateDestination?.let(navigateTo)
    }
}

@Composable
private fun LanguageContent(
    uiState: LanguageUiState.Success,
    hasBack: Boolean,
    navigateBack: () -> Unit
) {
    val selectedColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
    ) {
        Headline(
            modifier = Modifier.padding(horizontal = Spacing_8dp, vertical = Spacing_16dp),
            isBackEnabled = hasBack,
            title = TextBuilder.StringResource(R.string.language_title),
            onBackClick = { navigateBack() }
        )

        Text(
            modifier = Modifier.padding(vertical = Spacing_8dp, horizontal = Spacing_16dp),
            text = stringResource(R.string.language_subtitle),
            style = MaterialTheme.typography.bodyMedium,
        )

        MFTextField(
            modifier = Modifier.padding(top = Spacing_16dp),
            textFieldState = uiState.textFieldState,
            placeholder = stringResource(R.string.language_textfield_placeholder),
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Spacing_16dp),
                verticalArrangement = Arrangement.spacedBy(Spacing_4dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = uiState.languages,
                    key = { it.code },
                    contentType = { "language" }
                ) { language ->
                    Text(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { uiState.updateSelectedLanguage(language) }
                            .drawBehind {
                                if (language.code == uiState.selectedLanguage.value.code) {
                                    drawRect(selectedColor)
                                }
                            }
                            .padding(horizontal = Spacing_32dp, vertical = Spacing_16dp),
                        text = language.englishName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                item(key = "bottom_spacing") {
                    Spacer(
                        modifier = Modifier
                            .statusBarsPadding()
                            .height(80.dp)
                    )
                }
            }

            MVButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing_16dp)
                    .align(Alignment.BottomCenter),
                text = stringResource(R.string.language_select_button),
                onClick = { uiState.saveLanguage() }
            )
        }
    }
}

@Preview
@Composable
private fun LanguagePreview() {
    val uiState = remember { mutableStateOf(LanguageUiState.Loading) }

    MovieFlixTheme {
        LanguageScreen(
            uiState = uiState,
            hasBack = true,
            navigateTo = {},
            navigateBack = {}
        )
    }
}