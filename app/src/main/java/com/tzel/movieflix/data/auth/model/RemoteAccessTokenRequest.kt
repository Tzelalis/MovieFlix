package com.tzel.movieflix.data.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteAccessTokenRequest(
    @Json(name = "request_token")val requestToken: String,
)
