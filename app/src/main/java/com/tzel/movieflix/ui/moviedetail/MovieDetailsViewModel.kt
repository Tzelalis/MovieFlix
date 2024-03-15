package com.tzel.movieflix.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.moviedetail.mapper.MovieDetailsUiMapper
import com.tzel.movieflix.ui.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.moviedetail.model.SimilarMoviesPagingSource
import com.tzel.movieflix.ui.moviedetail.navigation.MovieDetailsIdArgument
import com.tzel.movieflix.usecase.movie.GetMovieDetailsUseCase
import com.tzel.movieflix.usecase.movie.GetReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieFullDetailsUseCase: GetMovieDetailsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val similarMoviesPagingSource: SimilarMoviesPagingSource,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val movieId = savedStateHandle.get<String>(MovieDetailsIdArgument) ?: ""
    private val similarMovies = Pager(PagingConfig(pageSize = PAGE_SIZE)) { similarMoviesPagingSource }.flow

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadMovieDetails(movieId)
        loadReviews(movieId)
        similarMoviesPagingSource.updateMovie(movieId)
    }

    private fun loadMovieDetails(movieId: String) {
        launch {
            val movieDetails = getMovieFullDetailsUseCase(movieId)

            _uiState.update {
                when (movieDetails) {
                    null -> MovieDetailsUiState.Error
                    else -> MovieDetailsUiState.Success(
                        movieDetails = movieDetailsUiMapper(movieDetails),
                        similarMovies = similarMovies
                    )
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

    companion object {
        private const val PAGE_SIZE = 20
    }
}