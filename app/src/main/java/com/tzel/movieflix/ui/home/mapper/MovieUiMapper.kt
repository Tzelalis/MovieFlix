package com.tzel.movieflix.ui.home.mapper

import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.ui.home.model.MovieUiItem
import javax.inject.Inject

class MovieUiMapper @Inject constructor() {
    operator fun invoke(movies: List<Movie>): List<MovieUiItem> {
        return movies.map { movie -> mapMovie(movie) }
    }

    private fun mapMovie(movie: Movie): MovieUiItem {
        return MovieUiItem(
            id = movie.id,
            title = movie.title,
            releaseDate = movie.releaseDate,
            backdropPath = IMAGE_BASE_URL + movie.backdropPath,
            posterPath = IMAGE_BASE_URL + movie.posterPath,
            adult = movie.adult,
            isFavorite = false  //todo add functionality for isFavorite
        )
    }

    companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}