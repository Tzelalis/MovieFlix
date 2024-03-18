package com.tzel.movieflix.ui.movie.home.model

import androidx.paging.PagingData
import com.tzel.movieflix.ui.core.composable.StringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val popularCategory: MoviesUiCategory,
    val genreMovies: List<MoviesUiCategory> = emptyList(),
    val test: StateFlow<List<MovieUiItem>> = MutableStateFlow(emptyList())
)

data class MoviesUiCategory(
    val name: StringResource,
    val movies: Flow<PagingData<MovieUiItem>>
)