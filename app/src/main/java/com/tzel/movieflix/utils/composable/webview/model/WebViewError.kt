package com.tzel.movieflix.utils.composable.webview.model

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import androidx.compose.runtime.Immutable

/**
 * A wrapper class to hold errors from the WebView.
 */
@Immutable
data class WebViewError(
    /**
     * The request the error came from.
     */
    val request: WebResourceRequest?,
    /**
     * The error that was reported.
     */
    val error: WebResourceError
)