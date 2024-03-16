package com.tzel.movieflix.ui.movie.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.movie.home.mapper.MovieUiMapper
import com.tzel.movieflix.ui.movie.home.model.MoviesPagingSource
import com.tzel.movieflix.ui.movie.moviedetail.mapper.MovieDetailsUiMapper
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsIdArgument
import com.tzel.movieflix.usecase.movie.GetMovieDetailsWithReviewsUseCase
import com.tzel.movieflix.usecase.movie.GetSimilarMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val getMovieDetailsWithReviewsUseCase: GetMovieDetailsWithReviewsUseCase,
    getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    moviesUiMapper: MovieUiMapper,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.get<String>(MovieDetailsIdArgument) ?: ""
    private val similarMovies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        MoviesPagingSource(
            getMovies = { getSimilarMoviesUseCase(movieId) },
            movieUiMapper = moviesUiMapper
        )
    }.flow

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadMovieDetails(movieId)
    }

    private fun loadMovieDetails(movieId: String) {
        launch {
            getMovieDetailsWithReviewsUseCase(movieId).collectLatest { movieDetails ->
                _uiState.update {
                    when (movieDetails) {
                        null -> MovieDetailsUiState.Error
                        else -> MovieDetailsUiState.Success(
                            movieDetails = movieDetailsUiMapper(movieDetails),
                            similarMovies = similarMovies,
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}