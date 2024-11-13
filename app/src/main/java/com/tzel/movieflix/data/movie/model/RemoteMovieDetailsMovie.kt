package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovieDetailsResponse(
    val id: String?,
    val title: String?,
    val adult: Boolean?,
    val genres: List<RemoteGenre?>?,
    val popularity: Double?,
    val overview: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val runtime: Int?,
    val budget: String?,
    @Json(name = "original_title") val originalTitle: String?,
    val status: String?,
    val tagline: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?,
    val credits: RemoteCastResponse?,
    val homepage: String?,
    val images: RemoteMovieImages
)