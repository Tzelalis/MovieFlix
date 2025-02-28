package com.tzel.movieflix.ui.dashboard.current.model

import androidx.annotation.DrawableRes
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.more.navigation.MoreDestination
import com.tzel.movieflix.ui.dashboard.movie.home.navigation.MoviesDestination
import com.tzel.movieflix.ui.dashboard.series.navigation.SeriesDestination

sealed class NavigationUiItem(
    @DrawableRes val iconRes: Int,
    val label: TextBuilder,
    val destination: NavigationDestination
) {
    data object Movies : NavigationUiItem(
        iconRes = R.drawable.ic_calendar,
        label = TextBuilder.StringResource(R.string.navigation_bar_movies),
        destination = MoviesDestination
    )

    data object Series : NavigationUiItem(
        iconRes = R.drawable.ic_calendar,
        label = TextBuilder.StringResource(R.string.navigation_bar_series),
        destination = SeriesDestination
    )

    data object More : NavigationUiItem(
        iconRes = R.drawable.ic_more,
        label = TextBuilder.StringResource(R.string.navigation_bar_more),
        destination = MoreDestination
    )

    companion object {
        val items = listOf(Movies, Series, More)
    }
}
