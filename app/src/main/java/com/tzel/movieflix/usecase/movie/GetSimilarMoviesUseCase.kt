package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import com.tzel.movieflix.usecase.core.UseCase
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val repo: MovieRepository) : UseCase {
    suspend operator fun invoke(movieId: String, page: Int = 1): MovieResult? {
        return try {
            repo.getSimilarMovies(movieId, page)
        } catch (e: Exception) {
            null
        }
    }
}