package com.tzel.movieflix.utils.ext

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import timber.log.Timber


fun Context.sharePlainText(url: String?) {
    if (url.isNullOrBlank()) return

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }

    try {
        startActivity(sendIntent)

    } catch (ex: ActivityNotFoundException) {
        Timber.tag("sharePlainText").e(ex)
    }
}

fun Context.openYoutubeVideo(videoId: String?) {
    if (videoId.isNullOrBlank()) return

    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
    try {
        startActivity(appIntent)
    } catch (ex: ActivityNotFoundException) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))

        try {
            startActivity(webIntent)
        } catch (ex: ActivityNotFoundException) {
            Timber.tag("openYoutubeVideo").e(ex)
        }
    }
}