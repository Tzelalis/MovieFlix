package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.MovieResult
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(movieId: String, page: Int = 1): MovieResult {
        return repo.getSimilarMovies(movieId, page)
    }
}