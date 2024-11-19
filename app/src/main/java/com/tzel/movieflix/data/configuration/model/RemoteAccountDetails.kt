package com.tzel.movieflix.data.configuration.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteAccountDetails(
    val id: String?,
    val username: String?,
    val name: String?,
    @Json(name = "include_adult") val includeAdult: Boolean?,
    @Json(name = "status_code") val statusCode: Int? = null,
)
