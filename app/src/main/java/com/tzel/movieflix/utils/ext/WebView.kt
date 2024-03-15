package com.tzel.movieflix.utils.ext

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import java.util.Locale

fun Context.openUrlInBrowser(url: String, title: String? = null) {
    val builder = CustomTabsIntent.Builder().apply {
        setColorScheme(CustomTabsIntent.COLOR_SCHEME_DARK)
        setDownloadButtonEnabled(false)
        setBookmarksButtonEnabled(false)
        setInstantAppsEnabled(false)
        setShowTitle(true)
        setTranslateLocale(Locale.getDefault())
    }
    val customTabsIntent = builder.build().apply {
        title?.let {
            intent.putExtra(Intent.EXTRA_TITLE, title)
        }
    }
    customTabsIntent.launchUrl(this, url.toUri())
}