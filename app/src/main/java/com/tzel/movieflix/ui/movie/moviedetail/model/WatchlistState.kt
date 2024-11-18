package com.tzel.movieflix.ui.movie.moviedetail.model

import androidx.annotation.DrawableRes
import com.tzel.movieflix.R

sealed class WatchlistState(@DrawableRes val iconRes: Int?) {
    data object Loading : WatchlistState(iconRes = null)
    data object Added : WatchlistState(iconRes = R.drawable.ic_check)
    data object Removed : WatchlistState(iconRes = R.drawable.ic_add)
}