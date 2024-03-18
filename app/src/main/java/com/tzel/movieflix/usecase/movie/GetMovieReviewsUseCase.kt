package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.ReviewsResult
import timber.log.Timber
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(movieId: String, page: Int = 1): ReviewsResult? {
        return try {
            repo.getMovieReviews(movieId, page)
        } catch (e: Exception){
            Timber.tag(GetMovieReviewsUseCase::class.java.simpleName).e(e)
            null
        }
    }
}