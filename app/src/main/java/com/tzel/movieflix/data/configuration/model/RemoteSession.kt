package com.tzel.movieflix.data.configuration.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSession(
    val success: Boolean?,
    @Json(name = "session_id")val sessionId: String?
)
