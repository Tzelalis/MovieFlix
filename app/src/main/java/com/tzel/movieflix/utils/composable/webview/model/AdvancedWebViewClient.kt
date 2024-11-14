package com.tzel.movieflix.utils.composable.webview.model

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * OpapWebViewClient
 *
 * A parent class implementation of WebViewClient that can be subclassed to add custom behaviour.
 *
 * As Accompanist Web needs to set its own web client to function, it provides this intermediary
 * class that can be overriden if further custom behaviour is required.
 */

open class AdvancedWebViewClient : WebViewClient() {
    open lateinit var state: WebViewState
        internal set
    open lateinit var navigator: WebViewNavigator
        internal set

    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        state.loadingState = LoadingState.Loading(0.0f)
        state.errorsForCurrentRequest.clear()
        state.connectionErrorForCurrentRequest = false
        state.pageTitle = null
        state.pageIcon = null

        state.lastLoadedUrl = url
    }

    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
        state.loadingState = LoadingState.Finished
    }

    override fun doUpdateVisitedHistory(view: WebView, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)

        navigator.canGoBack = view.canGoBack()
        navigator.canGoForward = view.canGoForward()
    }

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)

        if (error != null) {
            state.errorsForCurrentRequest.add(WebViewError(request, error))

            val isFromMainFrame = request?.isForMainFrame == true
            val hasConnectionError = error.errorCode in connectionErrorCodes
            state.connectionErrorForCurrentRequest = isFromMainFrame && hasConnectionError
        }
    }

    companion object {
        private const val ERR_INTERNET_DISCONNECTED = -2
        private const val ERR_INTERNET_ABORTED = -6
        private const val ERR_CONNECTION_TIMED_OUT = -8
        private val connectionErrorCodes = listOf(ERR_INTERNET_DISCONNECTED, ERR_INTERNET_ABORTED, ERR_CONNECTION_TIMED_OUT)
    }
}