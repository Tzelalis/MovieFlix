package com.tzel.movieflix.data.configuration.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSessionRequest(
    @Json(name = "request_token")val requestToken: String,
)
