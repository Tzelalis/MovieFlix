package com.tzel.movieflix.ui.home

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.home.mapper.MovieUiMapper
import com.tzel.movieflix.ui.home.model.HomeUiState
import com.tzel.movieflix.usecase.movie.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val movieUiMapper: MovieUiMapper,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        launch {
            val movies = getPopularMoviesUseCase(1)?.movies ?: return@launch

            _uiState.update {
                it.copy(popularMovies = movieUiMapper(movies))
            }
        }
    }
}