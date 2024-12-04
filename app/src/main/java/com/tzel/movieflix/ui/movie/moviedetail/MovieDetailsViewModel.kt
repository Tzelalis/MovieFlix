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
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchlistUiState
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.usecase.movie.GetMovieDetailsUseCase
import com.tzel.movieflix.usecase.movie.GetSimilarMoviesUseCase
import com.tzel.movieflix.usecase.movie.SetMovieFavoriteUseCase
import com.tzel.movieflix.usecase.user.AddToWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val setMovieFavoriteUseCase: SetMovieFavoriteUseCase,
    private val movieDetailsUiToMovieMapper: MovieDetailsUiToMovieMapper,
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

            val movieDetails = getMovieDetailsUseCase(
                movieId = movieId,
                includeCast = true,
                includeVideos = true,
                includeProviders = true,
                includeWatchlistState = true
            )

            _uiState.update {
                when (movieDetails) {
                    null -> MovieDetailsUiState.Error(refresh = { loadMovieDetails(movieId) })
                    else -> MovieDetailsUiState.Success(
                        movieDetails = movieDetailsUiMapper(movieDetails), // todo add region from user settings
                        similarMovies = similarMovies,
                        onFavoriteClick = ::setMovieFavorite,
                        addToWatchlist = ::updateMovieWatchlistStatus,
                        refresh = { loadMovieDetails(movieId) }
                    )
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
            if (state.movieDetails.watchlistUiState.value == WatchlistUiState.Loading) return@launch
            val isAdded = state.movieDetails.watchlistUiState.value == WatchlistUiState.Added

            state.movieDetails.watchlistUiState.value = WatchlistUiState.Loading

            val result = addToWatchlistUseCase(movieId = args.id, targetStatus = !isAdded)
            state.movieDetails.watchlistUiState.value = if (result) WatchlistUiState.Added else WatchlistUiState.Removed
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}