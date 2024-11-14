package com.tzel.movieflix.utils.composable.webview

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tzel.movieflix.utils.composable.webview.model.AdvancedChromeClient
import com.tzel.movieflix.utils.composable.webview.model.AdvancedWebViewClient
import com.tzel.movieflix.utils.composable.webview.model.WebContent
import com.tzel.movieflix.utils.composable.webview.model.WebViewNavigator
import com.tzel.movieflix.utils.composable.webview.model.WebViewState
import com.tzel.movieflix.utils.composable.webview.model.rememberWebViewNavigator

/**
 * A wrapper around the Android View WebView to provide a basic WebView composable.
 *
 * The WebView attempts to set the layoutParams based on the Compose modifier passed in. If it
 * is incorrectly sizing, use the layoutParams composable function instead.
 *
 * @param state The webview state holder where the Uri to load is defined.
 * @param modifier A compose modifier
 * @param captureBackPresses Set to true to have this Composable capture back presses and navigate
 * the WebView back.
 * @param navigator An optional navigator object that can be used to control the WebView's
 * navigation from outside the composable.
 * @param onCreated Called when the WebView is first created, this can be used to set additional
 * settings on the WebView. WebChromeClient and WebViewClient should not be set here as they will be
 * subsequently overwritten after this lambda is called.
 * @param onDispose Called when the WebView is destroyed. Provides a bundle which can be saved
 * if you need to save and restore state in this WebView.
 * @param client Provides access to WebViewClient via subclassing
 * @param chromeClient Provides access to WebChromeClient via subclassing
 * @param factory An optional WebView factory for using a custom subclass of WebView
 */
@Composable
fun WebView(
    modifier: Modifier = Modifier,
    state: WebViewState,
    captureBackPresses: Boolean = true,
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    supportZoom: Boolean = false,
    cacheMode: Int = WebSettings.LOAD_NO_CACHE,
    onCreated: (WebView) -> Unit = {},
    onDispose: (WebView) -> Unit = {},
    client: AdvancedWebViewClient = remember { AdvancedWebViewClient() },
    chromeClient: AdvancedChromeClient = remember { AdvancedChromeClient() },
    factory: ((Context) -> WebView)? = null,
) {
    BoxWithConstraints(modifier) {
        // WebView changes it's layout strategy based on
        // it's layoutParams. We convert from Compose Modifier to
        // layout params here.
        val width = if (constraints.hasFixedWidth)
            ViewGroup.LayoutParams.MATCH_PARENT
        else
            ViewGroup.LayoutParams.WRAP_CONTENT
        val height = if (constraints.hasFixedHeight)
            ViewGroup.LayoutParams.MATCH_PARENT
        else
            ViewGroup.LayoutParams.WRAP_CONTENT

        val layoutParams = FrameLayout.LayoutParams(
            width,
            height
        )

        WebView(
            state = state,
            layoutParams = layoutParams,
            modifier = Modifier,
            captureBackPresses = captureBackPresses,
            navigator = navigator,
            supportZoom = supportZoom,
            cacheMode = cacheMode,
            onCreated = onCreated,
            onDispose = onDispose,
            client = client,
            chromeClient = chromeClient,
            factory = factory
        )
    }
}

@Composable
private fun WebView(
    modifier: Modifier = Modifier,
    state: WebViewState,
    layoutParams: FrameLayout.LayoutParams,
    captureBackPresses: Boolean = true,
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    supportZoom: Boolean = false,
    cacheMode: Int = WebSettings.LOAD_NO_CACHE,
    onCreated: (WebView) -> Unit = {},
    onDispose: (WebView) -> Unit = {},
    client: AdvancedWebViewClient = remember { AdvancedWebViewClient() },
    chromeClient: AdvancedChromeClient = remember { AdvancedChromeClient() },
    factory: ((Context) -> WebView)? = null,
) {
    val webView = state.webView

    BackHandler(captureBackPresses && navigator.canGoBack) {
        webView?.goBack()
    }

    webView?.let { wv ->
        LaunchedEffect(wv, navigator) {
            with(navigator) {
                wv.handleNavigationEvents()
            }
        }

        LaunchedEffect(wv, state) {
            snapshotFlow { state.content }.collect { content ->
                when (content) {
                    is WebContent.Url -> {
                        wv.loadUrl(content.url, content.additionalHttpHeaders)
                    }

                    is WebContent.Data -> {
                        wv.loadDataWithBaseURL(
                            content.baseUrl,
                            content.data,
                            content.mimeType,
                            content.encoding,
                            content.historyUrl
                        )
                    }

                    is WebContent.Post -> {
                        wv.postUrl(
                            content.url,
                            content.postData
                        )
                    }

                    is WebContent.NavigatorOnly -> {
                        // NO-OP
                    }
                }
            }
        }
    }

    // Set the state of the client and chrome client
    // This is done internally to ensure they always are the same instance as the
    // parent Web composable
    client.state = state
    client.navigator = navigator
    chromeClient.state = state

    AndroidView(
        modifier = modifier,
        factory = { context ->
            (factory?.invoke(context) ?: WebView(context)).apply {
                onCreated(this)

                this.layoutParams = layoutParams

                state.viewState?.let {
                    this.restoreState(it)
                }

                webChromeClient = chromeClient
                webViewClient = client

                settings.apply {
                    this.setSupportZoom(supportZoom)
                    this.displayZoomControls = false
                    this.builtInZoomControls = false

                    this.cacheMode = cacheMode
                    this.javaScriptEnabled = true
                }
            }.also { state.webView = it }
        },
        onRelease = {
            onDispose(it)
        }
    )
}


