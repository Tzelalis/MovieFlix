package com.tzel.movieflix.data.user.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteWatchlistRequest(
    @Json(name = "media_id") val id: String,
    @Json(name = "media_type") val mediaType: String = "movie",
    val watchlist: Boolean = true,
)
