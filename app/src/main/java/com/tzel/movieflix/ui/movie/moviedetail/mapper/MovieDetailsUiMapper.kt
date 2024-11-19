package com.tzel.movieflix.ui.movie.moviedetail.mapper

import androidx.compose.runtime.mutableStateOf
import com.tzel.movieflix.domain.movie.entity.MovieDetails
import com.tzel.movieflix.ui.core.mapper.ImagePathMapper
import com.tzel.movieflix.ui.core.mapper.ImageSize
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchlistUiState
import java.util.Locale
import javax.inject.Inject

class MovieDetailsUiMapper @Inject constructor(
    private val imagePathMapper: ImagePathMapper,
    private val movieStatsUiMapper: MovieStatsUiMapper,
    private val reviewUiMapper: ReviewUiMapper,
    private val moviesImagesUiMapper: MoviesImagesUiMapper,
    private val watchProvidersUiMapper: WatchProvidersUiMapper,
) {
    operator fun invoke(details: MovieDetails, region: String = Locale.getDefault().country): MovieDetailsUi {
        return MovieDetailsUi(
            id = details.id,
            title = details.title,
            adult = details.adult,
            genres = details.genres,
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
            posterUrl = imagePathMapper(details.posterPath),
            images = details.images?.let { moviesImagesUiMapper(it) },
            videos = details.videos,
            watchProviders = watchProvidersUiMapper(providers = details.watchProviders, region = region),
            isFavorite = details.isFavorite,
            watchlistUiState = mutableStateOf(watchlistState(details.inWatchlist))
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

    private fun watchlistState(watchlist: Boolean?): WatchlistUiState {
        return when (watchlist) {
            true -> WatchlistUiState.Added
            false -> WatchlistUiState.Removed
            else -> WatchlistUiState.Loading
        }
    }

    companion object {
        private val DEFAULT_REGION_CODE = "US"
    }
}