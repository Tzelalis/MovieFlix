package com.tzel.movieflix.data.movie.mapper

import com.tzel.movieflix.data.movie.model.LocalMovie
import com.tzel.movieflix.domain.movie.entity.Movie
import javax.inject.Inject

class LocalMoviesToMoviesMapper @Inject constructor() {
    operator fun invoke(localMovies: List<LocalMovie>): List<Movie> {
        return localMovies.map { localMovie -> mapMovie(localMovie) }
    }

    private fun mapMovie(localMovie: LocalMovie): Movie {
        return Movie(
            id = localMovie.id.toString(),
            title = localMovie.title,
            releaseDate = localMovie.releaseDate,
            backdropPath = localMovie.backdropUrl,
            posterPath = localMovie.posterUrl,
            isFavorite = localMovie.isFavorite,
            genresIds = emptyList()
        )
    }
}