package com.tzel.movieflix.usecase.movie

import com.tzel.movieflix.domain.movie.MovieRepository
import com.tzel.movieflix.domain.movie.entity.Genre
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val repo: MovieRepository) {
    suspend operator fun invoke(): List<Genre> {
        return repo.getGenres()
    }
}