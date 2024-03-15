package com.tzel.movieflix.ui.moviedetail.mapper

import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.ui.core.ImagePathMapper
import com.tzel.movieflix.ui.core.ImageSize
import com.tzel.movieflix.ui.moviedetail.model.MovieDetailsUi
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor(
    private val imagePathMapper: ImagePathMapper,
    private val movieStatsUiMapper: MovieStatsUiMapper
) {
    operator fun invoke(details: MovieDetails): MovieDetailsUi {
        return MovieDetailsUi(
            id = details.id,
            title = details.title,
            adult = details.adult,
            genres = details.genres.joinToString(", "),
            popularity = details.popularity,
            overview = details.overview,
            imageUrl = mapImageUrl(
                details.backdropPath,
                details.posterPath,
            ),
            budget = details.budget,
            status = details.status,
            tagline = details.tagline,
            voteAverage = details.voteAverage,
            voteCount = details.voteCount,
            cast = details.cast.distinctBy { it.id },
            stats = movieStatsUiMapper(details.releaseDate, details.runtime),
            homepage = details.homepage
        )
    }

    private fun mapImageUrl(backdropPath: String?, posterPath: String?): String {
        return imagePathMapper(
            backdropPath,
            ImageSize.BackdropSize.W1280
        ) ?: imagePathMapper(
            posterPath,
            ImageSize.PosterSize.W500
        ) ?: ""
    }
}