package com.tzel.movieflix.data.auth.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteAccessTokenResponse(
    @Json(name = "access_token") val accessToken: String?,
    @Json(name = "account_id") val accountId: String?,
    val success: Boolean?,
    @Json(name = "status_code") val statusCode: Int?
)