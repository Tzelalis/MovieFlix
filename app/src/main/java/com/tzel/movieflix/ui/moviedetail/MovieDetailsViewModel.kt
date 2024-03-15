package com.tzel.movieflix.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.moviedetail.mapper.MovieDetailsUiMapper
import com.tzel.movieflix.ui.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.moviedetail.navigation.MovieDetailsIdArgument
import com.tzel.movieflix.usecase.movie.GetMovieDetailsUseCase
import com.tzel.movieflix.usecase.movie.GetReviewsUseCase
import com.tzel.movieflix.usecase.movie.GetSimilarMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieFullDetailsUseCase: GetMovieDetailsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.get<String>(MovieDetailsIdArgument) ?: ""

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadMovieDetails(movieId)
        loadReviews(movieId)
        loadSimilarMovies(movieId)
    }

    private fun loadMovieDetails(movieId: String) {
        launch {
            val movieDetails = getMovieFullDetailsUseCase(movieId)

            _uiState.update {
                when (movieDetails) {
                    null -> MovieDetailsUiState.Error
                    else -> MovieDetailsUiState.Success(movieDetailsUiMapper(movieDetails))
                }
            }
        }
    }

    fun loadReviews(movieId: String) {
        launch {
            val reviews = getReviewsUseCase(movieId)

            _uiState.update {
                it
            }
        }
    }

    fun loadSimilarMovies(movieId: String) {
        launch {
            val similarMovies = getSimilarMoviesUseCase(movieId)

            _uiState.update {
                it
            }
        }
    }
}