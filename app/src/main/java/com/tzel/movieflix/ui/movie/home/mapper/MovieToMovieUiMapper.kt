package com.tzel.movieflix.ui.movie.home.mapper

import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import com.tzel.movieflix.ui.core.mapper.ImageSize
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import javax.inject.Inject

class MovieToMovieUiMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) {
    operator fun invoke(movies: List<Movie>, page: Int): List<MovieUiItem> {
        return movies.map { movie -> mapMovie(movie, page) }.distinctBy { it.id }
    }

    private fun mapMovie(movie: Movie, page: Int): MovieUiItem {
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