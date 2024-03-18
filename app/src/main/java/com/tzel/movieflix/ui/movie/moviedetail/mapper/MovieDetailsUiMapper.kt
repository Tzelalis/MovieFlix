package com.tzel.movieflix.ui.movie.moviedetail.mapper

import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import com.tzel.movieflix.ui.core.mapper.ImageSize
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor(
    private val imagePathMapper: ImagePathMapper,
    private val movieStatsUiMapper: MovieStatsUiMapper,
    private val reviewUiMapper: ReviewUiMapper,
) {
    operator fun invoke(details: MovieDetails): MovieDetailsUi {
        return MovieDetailsUi(
            id = details.id,
            title = details.title,
            adult = details.adult,
            genres = details.genres.joinToString(separator = ", ", transform = { it.name }),
            popularity = details.popularity,
            overview = details.overview,
            imageUrl = mapImageUrl(
                details.backdropPath,
                details.posterPath,
            ),
            budget = details.budget,
            status = details.status,
            tagline = mapTagline(details.tagline),
            voteAverage = details.voteAverage,
            voteCount = details.voteCount,
            cast = details.cast.distinctBy { it.id },
            stats = movieStatsUiMapper(details.releaseDate, details.runtime, details.voteAverage),
            homepage = details.homepage,
            reviews = reviewUiMapper(details.reviews),
            isFavorite = details.isFavorite
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

    private fun mapTagline(tagline: String?): String? {
        if (tagline.isNullOrBlank()) return null

        return tagline
    }
}