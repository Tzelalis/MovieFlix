package com.tzel.movieflix.usecase.user

import com.tzel.movieflix.domain.user.UserRepository
import timber.log.Timber
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(private val repo: UserRepository) {
    suspend operator fun invoke(movieId: String, status: Boolean): Boolean {
        return try {
            val result = repo.addToWatchlist(movieId, status)
            return if (result) status else !status
        } catch (e: Exception) {
            Timber.tag(AddToWatchlistUseCase::class.java.simpleName).e(e)
            status
        }
    }
}