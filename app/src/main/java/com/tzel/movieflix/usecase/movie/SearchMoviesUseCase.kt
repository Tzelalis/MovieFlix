package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.usecase.core.UseCase
import timber.log.Timber
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val repo: MovieRepository) : UseCase {
    suspend operator fun invoke(title: String, page: Int = 1): MovieResult? {
        return try {
            repo.searchMovies(title, page)
        } catch (e: Exception) {
            Timber.tag(SearchMoviesUseCase::class.java.simpleName)
            null
        }
    }
}