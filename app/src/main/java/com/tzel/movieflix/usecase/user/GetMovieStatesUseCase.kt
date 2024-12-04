package com.tzel.movieflix.usecase.user

import com.tzel.movieflix.domain.user.UserRepository
import com.tzel.movieflix.domain.user.entity.MovieStates
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetMovieStatesUseCase @Inject constructor(private val repo: UserRepository) : UseCase {
    suspend operator fun invoke(movieId: String): MovieStates? {
        return try {
            repo.getMovieStates(movieId = movieId)
        } catch (e: Exception) {
            Timber.tag(GetWatchlistUseCase::class.java.simpleName).e(e)
            return null
        }
    }
}
