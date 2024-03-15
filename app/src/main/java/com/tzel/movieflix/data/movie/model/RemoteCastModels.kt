package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteCastResponse(
    val id: String?,
    val cast: List<RemoteCast?>?
)

@JsonClass(generateAdapter = true)
data class RemoteCast(
    val id: String?,
    @Json(name = "profile_path") val profilePath: String?,
    val name: String?,
    @Json(name = "original_name") val originalName: String?,
    val character: String?,
    val order: Int?,
)