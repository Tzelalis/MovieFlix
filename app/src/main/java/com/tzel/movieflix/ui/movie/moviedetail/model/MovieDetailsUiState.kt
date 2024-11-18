package com.tzel.movieflix.ui.movie.moviedetail.model

import androidx.paging.PagingData
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import kotlinx.coroutines.flow.Flow


sealed class MovieDetailsUiState(
    val onRefresh: () -> Unit
) {
    data class Loading(val refresh: () -> Unit) : MovieDetailsUiState(refresh)
    data class Success(
        val movieDetails: MovieDetailsUi,
        val similarMovies: Flow<PagingData<MovieUiItem>>,
        val onFavoriteClick: () -> Unit,
        val addToWatchlist: () -> Unit,
        val refresh: () -> Unit
    ) : MovieDetailsUiState(refresh)

    data class Error(val refresh: () -> Unit) : MovieDetailsUiState(refresh)
}