package com.tzel.movieflix.ui.movie.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.core.composable.StringResource
import com.tzel.movieflix.ui.movie.home.mapper.MovieToMovieUiMapper
import com.tzel.movieflix.ui.movie.home.model.HomeUiState
import com.tzel.movieflix.ui.movie.home.model.MoviesPagingSource
import com.tzel.movieflix.ui.movie.home.model.MoviesUiCategory
import com.tzel.movieflix.usecase.movie.GetGenresUseCase
import com.tzel.movieflix.usecase.movie.GetMoviesByGenreUseCase
import com.tzel.movieflix.usecase.movie.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            popularCategory = MoviesUiCategory(
                name = StringResource(R.string.home_popular_title),
                movies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    MoviesPagingSource(
                        movieToMovieUiMapper = moviesUiMapper,
                        getMovies = { page -> getPopularMoviesUseCase(page) })
                }.flow
            ),
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadMovieGenres()
    }

    private fun loadMovieGenres() {
        launch {
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
                    name = StringResource.Text(genre.name),
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