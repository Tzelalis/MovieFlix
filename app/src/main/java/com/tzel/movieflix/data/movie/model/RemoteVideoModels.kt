package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteVideoResponse(
    val results: List<RemoteVideoItem?>?
)

@JsonClass(generateAdapter = true)
data class RemoteVideoItem(
    val id: String?,
    val key: String?,
    val name: String?,
    val site: String?,
    val size: Int?,
    val type: String?,
    val official: Boolean?,
    @Json(name = "iso_639_1") val iso6391: String?,
    @Json(name = "iso_3166_1") val iso31661: String?,
)