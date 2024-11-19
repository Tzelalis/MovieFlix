package com.tzel.movieflix.data.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteTemporaryRequestTokenResponse(
    val success: Boolean?,
    @Json(name = "status_code") val statusCode: String?,
    @Json(name = "request_token") val token: String?,
)