package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import timber.log.Timber
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(page: Int): MovieResult? {
        return try {
            return repo.getPopularMovies(page)
        } catch (e: Exception) {
            Timber.tag(GetPopularMoviesUseCase::class.java.simpleName).e(e)
            null
        }
    }
}