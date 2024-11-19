package com.tzel.movieflix.ui.movie.moviedetail.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.domain.movie.entity.Genre
import com.tzel.movieflix.domain.movie.entity.VideoItem
import com.tzel.movieflix.domain.movie.entity.VideoSite
import com.tzel.movieflix.domain.movie.entity.VideoType
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.theme.GrayLight
import com.tzel.movieflix.ui.theme.RedMedium

data class MovieDetailsUi(
    val id: String,
    val imageUrl: String?,
    val posterUrl: String?,
    val genres: List<Genre>,
    val stats: List<MovieUiStats>,
    val overview: String?,
    val title: String?,
    val adult: Boolean,
    val popularity: Double?,
    val budget: String?,
    val status: String?,
    val tagline: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val cast: List<Cast>,
    val homepage: String?,
    val reviews: List<ReviewUi>,
    val images: MovieImagesUi?,
    val videos: List<VideoItem>,
    val isFavorite: Boolean,
    val watchlistUiState: MutableState<WatchlistUiState>
) {
    val favoriteColor: Color
        get() = if (isFavorite) RedMedium else GrayLight

    val genresText: String
        get() = genres.joinToString(separator = ", ", transform = { it.name })

    val trailerVideo: VideoItem?
        get() = videos.firstOrNull { it.type == VideoType.Trailer && it.site == VideoSite.YouTube }
}

data class MovieUiStats(
    @DrawableRes val icon: Int,
    val label: TextBuilder,
    val contentDescription: TextBuilder? = null
)

data class MovieImagesUi(
    val backdrops: List<MovieImageUiItem>,
    val posters: List<MovieImageUiItem>
)

data class MovieImageUiItem(
    val imageUrl: String,
    val aspectRatio: Double?,
    val height: Float?,
    val width: Float?
)