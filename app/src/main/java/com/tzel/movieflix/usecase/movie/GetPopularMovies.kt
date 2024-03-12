package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.framework.movie.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repo: MovieRepository,
) {
    suspend operator fun invoke() {

    }
}