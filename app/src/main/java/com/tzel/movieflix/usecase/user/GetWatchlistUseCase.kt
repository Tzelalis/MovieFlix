package com.tzel.movieflix.usecase.user

import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.usecase.auth.GetUserIdUseCase
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val repo: UserRepository,
    private val getUserIdUseCase: GetUserIdUseCase
) : UseCase {
    suspend operator fun invoke(page: Int = 1): MovieResult? {
        return try {
            val userId = getUserIdUseCase() ?: return null
            repo.getWatchlist(userId = userId, page = page)
        } catch (e: Exception) {
            Timber.tag(GetWatchlistUseCase::class.java.simpleName).e(e)
            return null
        }
    }
}
