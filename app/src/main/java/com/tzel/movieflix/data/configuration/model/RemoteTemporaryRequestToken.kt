package com.tzel.movieflix.data.configuration.model

import com.squareup.moshi.Json

data class RemoteTemporaryRequestToken(
    val success: Boolean?,
    val expiresAt: String?,
    @Json(name = "request_token") val token: String?,
)