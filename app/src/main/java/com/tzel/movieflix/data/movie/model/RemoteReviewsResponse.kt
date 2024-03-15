package com.tzel.movieflix.data.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteReviewsResponse(
    val page: Int?,
    @Json(name = "results")val reviews: List<RemoteReview?>?,
    @Json(name = "total_pages") val totalPages: Int?,
    @Json(name = "total_results") val totalResults: Int?
)

@JsonClass(generateAdapter = true)
data class RemoteReview(
    val id: String?,
    val author: String?,
    val content: String?,
    val url: String?
)