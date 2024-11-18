package com.tzel.movieflix.data.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteStatusResponse(
    val success: Boolean?,
    @Json(name = "status_code") val status: Int?,
    @Json(name = "status_message") val message: String?
)
