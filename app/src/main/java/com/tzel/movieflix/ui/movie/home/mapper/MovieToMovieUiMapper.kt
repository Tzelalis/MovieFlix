package com.tzel.movieflix.ui.movie.home.mapper

import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import com.tzel.movieflix.ui.core.mapper.ImageSize
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import javax.inject.Inject

class MovieToMovieUiMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) {
    operator fun invoke(movies: List<Movie>, page: Int): List<MovieUiItem> {
        return movies.mapNotNull { movie -> invoke(movie, page) }.distinctBy { it.id }
    }

    operator fun invoke(movie: Movie?, page: Int = 1): MovieUiItem? {
        if (movie == null) return null

        return MovieUiItem(
            id = movie.id,
            key = "${movie.id}$page",
            title = movie.title,
            releaseDate = movie.releaseDate,
            backdropPath = imagePathMapper(movie.backdropPath, ImageSize.BackdropSize.W1280),
            posterPath = imagePathMapper(movie.posterPath, ImageSize.PosterSize.W500),
        )
    }
}