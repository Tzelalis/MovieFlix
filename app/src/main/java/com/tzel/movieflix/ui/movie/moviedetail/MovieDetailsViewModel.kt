package com.tzel.movieflix.ui.movie.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.movie.home.mapper.MovieToMovieUiMapper
import com.tzel.movieflix.ui.movie.home.model.MoviesPagingSource
import com.tzel.movieflix.ui.movie.moviedetail.mapper.MovieDetailsUiMapper
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUiToMovieMapper
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchlistState
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.usecase.movie.GetMovieDetailsWithReviewsUseCase
import com.tzel.movieflix.usecase.movie.GetSimilarMoviesUseCase
import com.tzel.movieflix.usecase.movie.SetMovieFavoriteUseCase
import com.tzel.movieflix.usecase.user.AddToWatchlistUseCase
import com.tzel.movieflix.usecase.user.RateMovieUseCase
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
    private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase,
    private val movieDetailsUiToMovieMapper: MovieDetailsUiToMovieMapper,
    private val rateMovieUseCase: RateMovieUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    moviesUiMapper: MovieToMovieUiMapper,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val args = savedStateHandle.toRoute<MovieDetailsDestination>()

    private val similarMovies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        MoviesPagingSource(
            getMovies = { getSimilarMoviesUseCase(args.id) },
            movieToMovieUiMapper = moviesUiMapper
        )
    }.flow

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading(refresh = { loadMovieDetails(args.id) }))
    val uiState = _uiState.asStateFlow()

    init {
        loadMovieDetails(args.id)
    }

    private fun loadMovieDetails(movieId: String) {
        launch {
            _uiState.update { MovieDetailsUiState.Loading(refresh = { loadMovieDetails(movieId) }) }

            getMovieDetailsWithReviewsUseCase(movieId).collectLatest { movieDetails ->
                _uiState.update {
                    when (movieDetails) {
                        null -> MovieDetailsUiState.Error(refresh = { loadMovieDetails(movieId) })
                        else -> MovieDetailsUiState.Success(
                            movieDetails = movieDetailsUiMapper(movieDetails),
                            similarMovies = similarMovies,
                            onFavoriteClick = ::setMovieFavorite,
                            addToWatchlist = ::updateMovieWatchlistStatus,
                            refresh = { loadMovieDetails(movieId) }
                        )
                    }
                }
            }
        }
    }

    private fun setMovieFavorite() {
        launch {
            val movieDetailsUi = (uiState.value as? MovieDetailsUiState.Success)?.movieDetails ?: return@launch
            val movie = movieDetailsUiToMovieMapper(movieDetailsUi)
            setMovieFavoriteUseCase(movie)
        }
    }

    private fun updateMovieWatchlistStatus() {
        launch {
            val state = uiState.value as? MovieDetailsUiState.Success ?: return@launch
            if (state.movieDetails.watchlistState.value == WatchlistState.Loading) return@launch
            val isAdded = state.movieDetails.watchlistState.value == WatchlistState.Added

            state.movieDetails.watchlistState.value = WatchlistState.Loading

            val result = addToWatchlistUseCase(movieId = args.id, status = !isAdded)
            state.movieDetails.watchlistState.value = if (result) WatchlistState.Added else WatchlistState.Removed
        }
    }


    companion object {
        private const val PAGE_SIZE = 20
    }
}