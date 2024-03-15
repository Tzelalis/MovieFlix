package com.tzel.movieflix.domain.movie.entity

data class MovieDetails(
    val id: String,
    val title: String?,
    val adult: Boolean,
    val genres: List<String>,
    val popularity: Double?,
    val overview: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val budget: String?,
    val status: String?,
    val tagline: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val cast: List<Cast>,
    val homepage: String?,
    val reviews: List<Review>? = null,
)



