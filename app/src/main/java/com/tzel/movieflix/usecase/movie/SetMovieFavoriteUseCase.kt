package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.Movie
import javax.inject.Inject

class SetMovieFavoriteUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(movie: Movie) {
        repo.setFavoriteMovie(movie)
    }
}