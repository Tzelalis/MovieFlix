package com.tzel.movieflix.ui.movie.moviedetail.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Success(
        val movieDetails: MovieDetailsUi,
        val similarMovies: Flow<PagingData<SimilarMovieUiItem>>,
    ) : MovieDetailsUiState()

    data object Error : MovieDetailsUiState()
}