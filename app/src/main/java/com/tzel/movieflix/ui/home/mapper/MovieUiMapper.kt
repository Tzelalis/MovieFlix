package com.tzel.movieflix.ui.home.mapper

import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.ui.core.ImagePathMapper
import com.tzel.movieflix.ui.core.ImageSize
import com.tzel.movieflix.ui.home.model.MovieUiItem
import javax.inject.Inject

class MovieUiMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) {
    operator fun invoke(movies: List<Movie>, page: Int): List<MovieUiItem> {
        return movies.map { movie -> mapMovie(movie, page) }.distinctBy { it.id }
    }

    private fun mapMovie(movie: Movie, page: Int): MovieUiItem {
        return MovieUiItem(
            id = movie.id,
            tag = "${movie.id}$page",
            title = movie.title,
            releaseDate = movie.releaseDate,
            backdropPath = imagePathMapper(movie.backdropPath, ImageSize.BackdropSize.W1280),
            posterPath = imagePathMapper(movie.posterPath, ImageSize.PosterSize.W500),
            adult = movie.adult,
            isFavorite = false  //todo add functionality for isFavorite
        )
    }
}