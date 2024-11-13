package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.entity.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailsWithReviewsUseCase @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val getMovieFavoriteStatusUseCase: GetMovieFavoriteStatusUseCase
) {
    suspend operator fun invoke(movieId: String, page: Int = 1): Flow<MovieDetails?> {
        return flow {
            val movieDetails = getMovieDetailsUseCase(movieId, includeVideos = true)

            emit(movieDetails)

            if (movieDetails == null) return@flow

            val reviews = getMovieReviewsUseCase(movieId, page)?.reviews
            emit(movieDetails.copy(reviews = reviews))

            getMovieFavoriteStatusUseCase(movieId).firstOrNull()?.let { isFavorite ->
                emit(movieDetails.copy(isFavorite = isFavorite))
            }
        }.combine(getMovieFavoriteStatusUseCase(movieId)) { movieDetails, isFavorite ->
            movieDetails?.copy(isFavorite = isFavorite)
        }
    }
}