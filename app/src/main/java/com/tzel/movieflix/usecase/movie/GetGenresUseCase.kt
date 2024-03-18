package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.Genre
import timber.log.Timber
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(): List<Genre> {
        return try {
            repo.getGenres().take(10)
        } catch (e: Exception) {
            Timber.tag(GetGenresUseCase::class.java.simpleName).e(e)
            emptyList()
        }
    }
}