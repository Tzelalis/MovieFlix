package com.tzel.movieflix.ui.signin.model

import android.webkit.WebView
import com.tzel.movieflix.utils.composable.webview.model.AdvancedWebViewClient

class AuthenticationWebViewClient(val saveSession: () -> Unit) : AdvancedWebViewClient() {
    override fun onPageFinished(view: WebView, url: String?) {
        if (url?.contains("/allow") == true) {
            saveSession()
        }

        super.onPageFinished(view, url)

    }
}