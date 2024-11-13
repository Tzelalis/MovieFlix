package com.tzel.movieflix.ui.movie.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.R
import com.tzel.movieflix.domain.movie.entity.TimeWindow
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.movie.home.mapper.MovieToMovieUiMapper
import com.tzel.movieflix.ui.movie.home.model.HomeUiState
import com.tzel.movieflix.ui.movie.home.model.MoviesPagingSource
import com.tzel.movieflix.ui.movie.home.model.MoviesUiCategory
import com.tzel.movieflix.ui.movie.moviedetail.mapper.MovieDetailsUiMapper
import com.tzel.movieflix.usecase.movie.GetGenresUseCase
import com.tzel.movieflix.usecase.movie.GetMoviesByGenreUseCase
import com.tzel.movieflix.usecase.movie.GetPopularMoviesUseCase
import com.tzel.movieflix.usecase.movie.trending.GetFirstTrendMovieUseCase
import com.tzel.movieflix.usecase.movie.trending.GetTrendingMoviesUseCase
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
    private val movieDetailsUiMapper: MovieDetailsUiMapper
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(onRefreshClick = ::refreshContent))
    val uiState = _uiState.asStateFlow()

    private var popularMoviesJob: Job? = null
    private var genreMoviesJob: Job? = null
    private var trendingMoviesJob: Job? = null

    init {
        refreshContent()
    }

    private fun refreshContent() {
        loadTrendingOfDay()
        loadPopularMovies()
        loadTrendingMovies()
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
                }.flow
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
                }.flow
            )

            _uiState.update { it.copy(trendingCategory = trending) }
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
                }.flow

                val category = MoviesUiCategory(
                    name = TextBuilder.Text(genre.name),
                    movies = pagingSource
                )
                movieGenrePagingSources.add(category)
            }

            _uiState.update { it.copy(genreMovies = movieGenrePagingSources) }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}