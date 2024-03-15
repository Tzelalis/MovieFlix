package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import timber.log.Timber
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(genre: String, page: Int = 1): MovieResult? {
        return try {
            repo.getMoviesByGenre(genre, page)
        } catch (e: Exception) {
            Timber.tag(GetMoviesByGenreUseCase::class.java.simpleName).e(e)
            null
        }
    }
}