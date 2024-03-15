package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.RemoteReview
import com.tzel.movieflix.data.movie.model.RemoteReviewsResponse
import com.tzel.movieflix.domain.movie.entity.Review
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import javax.inject.Inject

class RemoteReviewsMapper @Inject constructor() {
    operator fun invoke(response: RemoteReviewsResponse): ReviewsResult {
        if (response.page == null || response.totalPages == null) throw IllegalArgumentException("Invalid response")

        return ReviewsResult(
            page = response.page,
            reviews = response.reviews?.mapNotNull { mapReview(it) } ?: emptyList(),
            totalPages = response.totalPages,
            totalResults = response.totalResults ?: 0
        )
    }

    private fun mapReview(review: RemoteReview?): Review {
        if (review?.id == null || review.author == null || review.content == null) throw IllegalArgumentException("Invalid review")

        return Review(
            id = review.id,
            author = review.author,
            content = review.content,
            url = review.url
        )
    }
}