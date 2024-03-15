package com.tzel.movieflix.ui.moviedetail.model


sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Success(val movieDetails: MovieDetailsUi) : MovieDetailsUiState()
    data object Error : MovieDetailsUiState()
}