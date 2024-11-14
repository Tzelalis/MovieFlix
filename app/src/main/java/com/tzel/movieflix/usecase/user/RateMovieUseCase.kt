package com.tzel.movieflix.usecase.user

import androidx.annotation.FloatRange
import timber.log.Timber
import javax.inject.Inject

class RateMovieUseCase @Inject constructor() {
    operator fun invoke(
        movieId: String,
        @FloatRange(0.5, 10.0) rate: Double,
    ): Boolean {
        return try {

            true
        } catch (e: Exception) {
            Timber.tag(RateMovieUseCase::class.java.simpleName).e(e)
            false
        }
    }
}