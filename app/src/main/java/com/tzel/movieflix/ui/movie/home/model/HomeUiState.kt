package com.tzel.movieflix.ui.movie.home.model

import androidx.paging.PagingData
import com.tzel.movieflix.ui.core.composable.TextBuilder
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val popularCategory: MoviesUiCategory? = null,
    val genreMovies: List<MoviesUiCategory> = emptyList(),
    val onRefreshClick: () -> Unit
)

data class MoviesUiCategory(
    val name: TextBuilder,
    val movies: Flow<PagingData<MovieUiItem>>
)