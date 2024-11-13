package com.tzel.movieflix.domain.movie.entity

data class MovieImages(
    val backdrops: List<MovieImageItem>,
    val posters: List<MovieImageItem>,
)

data class MovieImageItem(
    val filePath: String,
    val aspectRatio: Double?,
    val height: Float?,
    val width: Float?,
)