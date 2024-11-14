package com.tzel.movieflix.utils.composable.webview.model

import android.content.Context
import android.net.Uri
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView

class MFWebViewClient : AdvancedWebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return handleCustomSchema(
            uri = request?.url,
            context = view?.context
        )
    }

    private fun handleCustomSchema(
        uri: Uri?,
        context: Context?,
    ): Boolean {
        if (uri == null) return false

        val url = uri.toString()
        val isNetworkAndNotPdf = URLUtil.isNetworkUrl(url) && !url.endsWith(PDF_SUFFIX)
        if (isNetworkAndNotPdf) return false

        // TODO add openToExternalBrowser
        //context?.openToExternalBrowser(url)
        return context != null
    }

    companion object {
        private const val PDF_SUFFIX = ".pdf"
    }
}