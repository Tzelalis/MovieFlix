package com.tzel.movieflix.usecase.user

import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.domain.user.entity.MovieStates
import timber.log.Timber
import javax.inject.Inject

class GetMovieStatesUseCase @Inject constructor(private val repo: UserRepository) {
    suspend operator fun invoke(movieId: String): MovieStates? {
        return try {
            repo.getMovieStates(movieId = movieId)
        } catch (e: Exception) {
            Timber.tag(GetWatchlistUseCase::class.java.simpleName).e(e)
            return null
        }
    }
}
