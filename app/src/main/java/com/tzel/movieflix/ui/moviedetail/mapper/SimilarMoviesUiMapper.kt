package com.tzel.movieflix.ui.moviedetail.mapper

import com.tzel.movieflix.domain.movie.entity.Movie
import com.tzel.movieflix.ui.core.ImagePathMapper
import com.tzel.movieflix.ui.moviedetail.model.SimilarMovieUiItem
import javax.inject.Inject

class SimilarMoviesUiMapper @Inject constructor(private val imagePathMapper: ImagePathMapper) {
    operator fun invoke(movie: List<Movie>, page: Int): List<SimilarMovieUiItem> {
        return movie.mapNotNull {
            val image = imagePathMapper(it.posterPath) ?: imagePathMapper(it.backdropPath) ?: return@mapNotNull null

            SimilarMovieUiItem(
                id = it.id,
                key = "${it.id}$page",
                imageUrl = image,
                contentDescription = it.title
            )
        }.distinctBy { it.key }
    }
}
