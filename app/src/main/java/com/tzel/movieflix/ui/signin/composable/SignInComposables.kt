package com.tzel.movieflix.ui.signin.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.LoadingContent
import com.tzel.movieflix.ui.signin.model.AuthenticationWebViewClient
import com.tzel.movieflix.ui.signin.model.SignInUiState
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.utils.composable.webview.WebView
import com.tzel.movieflix.utils.composable.webview.model.rememberWebViewState

@Composable
fun SignInScreen(
    uiState: State<SignInUiState>,
    navigateBack: () -> Unit,
) {
    Box(modifier = Modifier.animateContentSize()) {
        Box(
            modifier = Modifier
                .heightIn(min = 200.dp)
                .animateContentSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer),
        ) {
            when (val state = uiState.value) {
                SignInUiState.Loading -> LoadingContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .wrapContentSize()
                )

                SignInUiState.Error -> SignInResultContent(
                    iconRes = R.drawable.ic_round_close,
                    iconTint = MaterialTheme.colorScheme.error,
                    subtitle = stringResource(id = R.string.generic_error),
                    buttonText = stringResource(R.string.sign_in_error_button),
                    onButtonClick = { navigateBack() }
                )

                SignInUiState.SuccessSignIn -> SignInResultContent(
                    iconRes = R.drawable.ic_check,
                    iconTint = Color.Green,
                    subtitle = stringResource(R.string.sign_in_success_subtitle),
                    buttonText = stringResource(R.string.sign_in_success_button),
                    onButtonClick = { navigateBack() }
                )

                is SignInUiState.Idle -> SignInContent(state)
            }
        }
    }
}

@Composable
fun SignInContent(uiState: SignInUiState.Idle) {
    uiState.permissionUrl?.let { url ->
        val webViewState = rememberWebViewState(url)

        WebView(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            client = AuthenticationWebViewClient(
                saveSession = { uiState.createSession() }
            ),
            state = webViewState
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    val uiState = remember { mutableStateOf(SignInUiState.Error) }
    MovieFlixTheme {
        SignInScreen(
            uiState = uiState,
            navigateBack = {}
        )
    }
}