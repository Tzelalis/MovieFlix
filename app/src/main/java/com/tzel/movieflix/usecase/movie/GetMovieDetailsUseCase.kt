package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import timber.log.Timber
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repo: MovieRepository,
) {
    suspend operator fun invoke(movieId: String): MovieDetails? {
        return try {
            repo.getMovieDetails(movieId)
        } catch (e: Exception) {
            Timber.tag(GetMovieDetailsUseCase::class.java.simpleName).e(e)
            null
        }
    }
}