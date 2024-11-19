package com.tzel.movieflix.data.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteSessionResponse(
    @Json(name = "session_id") val sessionId: String?,
    val success: Boolean?,
    val failure: Boolean?,
    @Json(name = "status_code") val statusCode: Int?
)
