package com.tzel.movieflix.ui.search.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.paging.PagingData
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import kotlinx.coroutines.flow.Flow

data class SearchUiState(
    val textFieldState: TextFieldState = TextFieldState(),
    val movies: Flow<PagingData<MovieUiItem>>? = null,
)