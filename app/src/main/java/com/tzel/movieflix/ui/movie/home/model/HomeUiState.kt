package com.tzel.movieflix.ui.movie.home.model

import androidx.paging.PagingData
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import kotlinx.coroutines.flow.Flow

data class HomeUiState(
    val trendMovie: MovieDetailsUi? = null,
    val popularCategory: MoviesUiCategory? = null,
    val trendingCategory: MoviesUiCategory? = null,
    val genreMovies: List<MoviesUiCategory> = emptyList(),
    val onRefreshClick: () -> Unit
) {
    val firstSectionGenres: List<MoviesUiCategory>
        get() = genreMovies.take(2)

    val secondSectionGenres: List<MoviesUiCategory>
        get() = genreMovies.drop(2)

    val trendBackground: String?
        get() = trendMovie?.images?.posters?.firstOrNull { it.imageUrl != trendMovie.posterUrl }?.imageUrl ?: trendMovie?.posterUrl
}

data class MoviesUiCategory(
    val name: TextBuilder,
    val movies: Flow<PagingData<MovieUiItem>>
)