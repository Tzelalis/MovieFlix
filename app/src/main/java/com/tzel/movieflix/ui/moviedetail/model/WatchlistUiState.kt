package com.tzel.movieflix.ui.moviedetail.model

import androidx.annotation.DrawableRes
import com.tzel.movieflix.R

sealed class WatchlistUiState(@DrawableRes val iconRes: Int?) {
    data object Loading : WatchlistUiState(iconRes = null)
    data object Added : WatchlistUiState(iconRes = R.drawable.ic_check)
    data object Removed : WatchlistUiState(iconRes = R.drawable.ic_add)
}