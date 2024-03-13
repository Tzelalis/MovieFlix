package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieResponse(
    val page: Int?,
    @Json(name = "results")val movies: List<RemoteMovie?>?,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?
)

@JsonClass(generateAdapter = true)
data class RemoteMovie(
    val id: String?,
    val title: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "poster_path") val posterPath: String?,
    val adult: Boolean?,
)