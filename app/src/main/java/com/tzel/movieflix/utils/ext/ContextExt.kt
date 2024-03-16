package com.tzel.movieflix.utils.ext

import android.content.Context
import android.content.Intent

fun Context.sharePlainText(url: String?) {
    if (url.isNullOrBlank()) return

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }

    if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(sendIntent)
    }
}