package com.tzel.movieflix.ui.movie.moviedetail.model

sealed class MovieReviewsUiState {
    data object Loading : MovieReviewsUiState()
    data class Success(val reviews: List<ReviewUi>) : MovieReviewsUiState()
    data class Error(val message: String) : MovieReviewsUiState()
}
