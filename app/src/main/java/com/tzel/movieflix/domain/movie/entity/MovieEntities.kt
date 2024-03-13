package com.tzel.movieflix.domain.movie.entity

data class MovieResult(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)

data class Movie(
    val id: String,
    val title: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val adult: Boolean,
)
