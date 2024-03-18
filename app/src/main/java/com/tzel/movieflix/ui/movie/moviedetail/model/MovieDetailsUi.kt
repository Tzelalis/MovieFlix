package com.tzel.movieflix.ui.movie.moviedetail.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.ui.theme.GrayLight
import com.tzel.movieflix.ui.theme.RedMedium

data class MovieDetailsUi(
    val id: String,
    val imageUrl: String?,
    val genres: String,
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
    val isFavorite: Boolean
) {
    val favoriteColor: Color
        get() = if (isFavorite) RedMedium else GrayLight
}

data class MovieUiStats(
    @DrawableRes val icon: Int,
    val label: String,
    val contentDescription: String? = null
)