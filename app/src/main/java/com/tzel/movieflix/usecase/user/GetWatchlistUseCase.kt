package com.tzel.movieflix.usecase.user

import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.usecase.auth.GetUserIdUseCase
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val repo: UserRepository,
    private val getUserIdUseCase: GetUserIdUseCase
) {
    suspend operator fun invoke(page: Int = 1): MovieResult? {
        val userId = getUserIdUseCase() ?: return null
        return repo.getWatchlist(userId = userId, page = page)
    }
}
