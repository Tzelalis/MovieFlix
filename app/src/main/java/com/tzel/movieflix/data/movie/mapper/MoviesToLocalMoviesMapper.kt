package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.domain.movie.entity.Movie
import javax.inject.Inject

class MoviesToLocalMoviesMapper @Inject constructor() {
    operator fun invoke(movies: List<Movie>, isPopular: Boolean): List<LocalMovie> {
        return movies.mapNotNull { movie -> mapMovie(movie, isPopular) }
    }

    fun mapMovie(movie: Movie, isPopular: Boolean = false): LocalMovie? {
        val id = movie.id.toLongOrNull() ?: return null

        return LocalMovie(
            id = id,
            title = movie.title,
            releaseDate = movie.releaseDate,
            backdropUrl = movie.backdropPath,
            posterUrl = movie.posterPath,
            isPopular = isPopular,
            isFavorite = movie.isFavorite
        )
    }
}