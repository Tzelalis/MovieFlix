package com.tzel.movieflix.domain.movie.entity

data class ReviewsResult(
    val page: Int,
    val reviews: List<Review>,
    val totalPages: Int,
    val totalResults: Int
)

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val url: String?
)