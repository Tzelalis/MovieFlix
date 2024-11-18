package com.tzel.movieflix.data.user.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRateMovieRequest(
    @Json(name = "value") val rating: Double
)
