package com.tzel.movieflix.data.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSessionRequest(
    @Json(name = "access_token") val accessToken: String
)
