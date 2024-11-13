package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import timber.log.Timber
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(page: Int = 1): MovieResult? {
        return try {
            repo.upcomingMovies(page)
        } catch (e: Exception) {
            Timber.tag(GetUpcomingMoviesUseCase::class.java.simpleName).e(e)
            null
        }
    }
}