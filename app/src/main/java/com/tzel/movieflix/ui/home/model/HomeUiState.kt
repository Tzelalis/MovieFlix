package com.tzel.movieflix.ui.home.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val popularMovies: Flow<PagingData<MovieUiItem>>,
)
