package com.tzel.movieflix.usecase.movie.trending

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(private val repo: MovieRepository) : UseCase {
    suspend operator fun invoke(timeWindow: TimeWindow = TimeWindow.Day, page: Int = 1): MovieResult? {
        return try {
            repo.getTrendingMovies(timeWindow, page)
        } catch (e: Exception) {
            Timber.tag(GetTrendingMoviesUseCase::class.java.simpleName).e(e)
            null
        }
    }
}