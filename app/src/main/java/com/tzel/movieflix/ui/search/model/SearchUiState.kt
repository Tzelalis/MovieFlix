package com.tzel.movieflix.ui.search.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class SearchUiState(
    val textFieldState: TextFieldState = TextFieldState(),
    val movies: Flow<PagingData<com.tzel.movieflix.ui.dashboard.movie.home.model.MovieUiItem>>? = null,
)