package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(movieId: String, page: Int = 1): ReviewsResult {
        return repo.getMovieReviews(movieId, page)
    }
}