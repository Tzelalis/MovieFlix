package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieImages(
    val backdrops: List<RemoteMovieImageItem?>?,
    val posters: List<RemoteMovieImageItem?>?,
)

@JsonClass(generateAdapter = true)
data class RemoteMovieImageItem(
    @Json(name = "file_path") val filePath: String?,
    @Json(name = "aspect_ratio") val aspectRatio: Double?,
    val height: Float?,
    val width: Float?,
)