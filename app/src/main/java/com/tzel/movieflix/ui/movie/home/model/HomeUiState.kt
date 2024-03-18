package com.tzel.movieflix.ui.movie.home.model

import androidx.paging.PagingData
import com.tzel.movieflix.ui.core.composable.StringResource
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val popularCategory: MoviesUiCategory? = null,
    val genreMovies: List<MoviesUiCategory> = emptyList(),
    val onRefreshClick: () -> Unit
)

data class MoviesUiCategory(
    val name: StringResource,
    val movies: Flow<PagingData<MovieUiItem>>
)