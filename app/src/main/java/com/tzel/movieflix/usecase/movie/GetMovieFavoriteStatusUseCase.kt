package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.usecase.core.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import javax.inject.Inject

class GetMovieFavoriteStatusUseCase @Inject constructor(private val repo: MovieRepository) : UseCase {
    operator fun invoke(movieId: String): Flow<Boolean> {
        return try {
            repo.getMovieFavoriteStatus(movieId)
        } catch (e: Exception) {
            Timber.tag(GetMovieFavoriteStatusUseCase::class.java.simpleName).e(e)
            flowOf(false)
        }
    }
}