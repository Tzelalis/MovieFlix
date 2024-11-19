package com.tzel.movieflix.usecase.user

import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.usecase.auth.GetUserIdUseCase
import timber.log.Timber
import javax.inject.Inject

class AddToWatchlistUseCase @Inject constructor(
    private val repo: UserRepository,
    private val getUserId: GetUserIdUseCase,
) {
    suspend operator fun invoke(movieId: String, targetStatus: Boolean): Boolean {
        return try {
            val userId = getUserId() ?: throw Exception("User not found")
            val result = repo.addToWatchlist(userId = userId, movieId = movieId, status = targetStatus) ?: return !targetStatus
            return result
        } catch (e: Exception) {
            Timber.tag(AddToWatchlistUseCase::class.java.simpleName).e(e)
            !targetStatus
        }
    }
}