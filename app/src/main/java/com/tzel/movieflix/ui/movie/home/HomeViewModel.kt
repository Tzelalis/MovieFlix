package com.tzel.movieflix.ui.movie.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tzel.movieflix.R
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.movie.home.mapper.MovieToMovieUiMapper
import com.tzel.movieflix.ui.movie.home.model.HomeUiState
import com.tzel.movieflix.ui.movie.home.model.MoviesPagingSource
import com.tzel.movieflix.ui.movie.home.model.MoviesUiCategory
import com.tzel.movieflix.ui.movie.moviedetail.mapper.MovieDetailsUiMapper
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchlistUiState
import com.tzel.movieflix.usecase.movie.GetGenresUseCase
import com.tzel.movieflix.usecase.movie.GetMoviesByGenreUseCase
import com.tzel.movieflix.usecase.movie.GetPopularMoviesUseCase
import com.tzel.movieflix.usecase.movie.trending.GetFirstTrendMovieUseCase
import com.tzel.movieflix.usecase.movie.trending.GetTrendingMoviesUseCase
import com.tzel.movieflix.usecase.user.AddToWatchlistUseCase
import com.tzel.movieflix.usecase.user.GetWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val moviesUiMapper: MovieToMovieUiMapper,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val getFirstTrendMovieUseCase: GetFirstTrendMovieUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val getWatchlistUseCase: GetWatchlistUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            addToWatchlist = ::addToWatchList
        )
    )

    val uiState = _uiState.asStateFlow()

    private var popularMoviesJob: Job? = null
    private var genreMoviesJob: Job? = null
    private var trendingMoviesJob: Job? = null
    private var watchlistMoviesJob: Job? = null

    init {
        refreshContent()
    }

    private fun refreshContent() {
        loadTrendingOfDay()
        loadPopularMovies()
        loadTrendingMovies()
        loadWatchlistMovies()
        loadMovieGenres()
    }

    private fun loadTrendingOfDay() {
        launch {
            val trending = getFirstTrendMovieUseCase() ?: return@launch
            _uiState.update { it.copy(trendMovie = movieDetailsUiMapper(trending)) }
        }
    }

    private fun loadPopularMovies() {
        popularMoviesJob?.cancel()
        popularMoviesJob = launch {
            val popular = MoviesUiCategory(
                name = TextBuilder.StringResource(R.string.home_popular_title),
                movies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    MoviesPagingSource(
                        movieToMovieUiMapper = moviesUiMapper,
                        getMovies = { page -> getPopularMoviesUseCase(page) })
                }.flow.cachedIn(viewModelScope)
            )

            _uiState.update { it.copy(popularCategory = popular) }
        }
    }

    private fun loadTrendingMovies() {
        trendingMoviesJob?.cancel()
        trendingMoviesJob = launch {
            val trending = MoviesUiCategory(
                name = TextBuilder.StringResource(R.string.home_trending_title),
                movies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    MoviesPagingSource(
                        movieToMovieUiMapper = moviesUiMapper,
                        getMovies = { page -> getTrendingMoviesUseCase(TimeWindow.Week, page) })
                }.flow.cachedIn(viewModelScope)
            )

            _uiState.update { it.copy(trendingCategory = trending) }
        }
    }

    private fun loadWatchlistMovies() {
        watchlistMoviesJob?.cancel()
        watchlistMoviesJob = launch {
            val watchlist = MoviesUiCategory(
                name = TextBuilder.StringResource(R.string.home_watchlist_title),
                movies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    MoviesPagingSource(
                        movieToMovieUiMapper = moviesUiMapper,
                        getMovies = { page -> getWatchlistUseCase(page) })
                }.flow.cachedIn(viewModelScope)
            )

            _uiState.update { it.copy(watchlistCategory = watchlist) }
        }
    }

    private fun loadMovieGenres() {
        genreMoviesJob?.cancel()
        genreMoviesJob = launch {
            val genres = getGenresUseCase()

            val movieGenrePagingSources = mutableListOf<MoviesUiCategory>()

            genres.forEach { genre ->
                val pagingSource = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    MoviesPagingSource(
                        movieToMovieUiMapper = moviesUiMapper,
                        getMovies = { page ->
                            getMoviesByGenreUseCase(genre.id, page)
                        })
                }.flow.cachedIn(viewModelScope)

                val category = MoviesUiCategory(
                    name = TextBuilder.Text(genre.name),
                    movies = pagingSource
                )
                movieGenrePagingSources.add(category)
            }

            _uiState.update { it.copy(genreMovies = movieGenrePagingSources) }
        }
    }

    private fun addToWatchList(movieDetailsUi: MovieDetailsUi) {
        launch {
            if (movieDetailsUi.watchlistUiState.value == WatchlistUiState.Loading) return@launch
            val isAdded = movieDetailsUi.watchlistUiState.value == WatchlistUiState.Added

            movieDetailsUi.watchlistUiState.value = WatchlistUiState.Loading

            val response = addToWatchlistUseCase(movieDetailsUi.id, !isAdded)
            val watchlistState = if (response) WatchlistUiState.Added else WatchlistUiState.Removed
            movieDetailsUi.watchlistUiState.value = watchlistState
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}