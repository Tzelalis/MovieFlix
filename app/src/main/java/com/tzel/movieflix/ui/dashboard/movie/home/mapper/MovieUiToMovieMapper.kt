package com.tzel.movieflix.ui.dashboard.movie.home.mapper

import com.tzel.movieflix.domain.core.Mapper
import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.ui.dashboard.movie.home.model.MovieUiItem
import javax.inject.Inject

class MovieUiToMovieMapper @Inject constructor() : Mapper {
    operator fun invoke(movie: MovieUiItem): Movie {
        return Movie(
            id = movie.id,
            title = movie.title,
            releaseDate = movie.releaseDate,
            backdropPath = movie.backdropPath,
            posterPath = movie.posterPath,
            genresIds = emptyList()
        )
    }
}