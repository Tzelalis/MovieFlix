package com.tzel.movieflix.usecase.user

import androidx.annotation.FloatRange
import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class RateMovieUseCase @Inject constructor(private val repo: UserRepository) : UseCase {
    suspend operator fun invoke(
        movieId: String,
        @FloatRange(0.5, 10.0) rate: Double,
    ): Boolean {
        return try {
            repo.rateMovie(movieId = movieId, rating = rate)
            true
        } catch (e: Exception) {
            Timber.tag(RateMovieUseCase::class.java.simpleName).e(e)
            false
        }
    }
}