package com.tzel.movieflix.utils.composable.webview.model

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView

/**
 * A parent class implementation of WebChromeClient that can be subclassed to add custom behaviour.
 *
 * As Accompanist Web needs to set its own web client to function, it provides this intermediary
 * class that can be overriden if further custom behaviour is required.
 */
open class AdvancedChromeClient : WebChromeClient() {
    open lateinit var state: WebViewState
        internal set

    override fun onReceivedTitle(view: WebView, title: String?) {
        super.onReceivedTitle(view, title)
        state.pageTitle = title
    }

    override fun onReceivedIcon(view: WebView, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
        state.pageIcon = icon
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)

        val loadingState = if (newProgress == 100) LoadingState.Finished else LoadingState.Loading(newProgress / 100f)
        state.loadingState = loadingState
    }
}