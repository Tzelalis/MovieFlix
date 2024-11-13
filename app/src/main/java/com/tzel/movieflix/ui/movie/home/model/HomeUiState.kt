package com.tzel.movieflix.ui.movie.home.model

import androidx.paging.PagingData
import com.tzel.movieflix.ui.core.composable.TextBuilder
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val trendMovie: MovieUiItem? = null,
    val popularCategory: MoviesUiCategory? = null,
    val trendingCategory: MoviesUiCategory? = null,
    val genreMovies: List<MoviesUiCategory> = emptyList(),
    val onRefreshClick: () -> Unit
) {
    val firstSectionGenres: List<MoviesUiCategory>
        get() = genreMovies.take(2)

    val secondSectionGenres: List<MoviesUiCategory>
        get() = genreMovies.drop(2)
}

data class MoviesUiCategory(
    val name: TextBuilder,
    val movies: Flow<PagingData<MovieUiItem>>
)