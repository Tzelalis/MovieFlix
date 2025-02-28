package com.tzel.movieflix.ui.search

import androidx.compose.runtime.snapshotFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.dashboard.movie.home.mapper.MovieToMovieUiMapper
import com.tzel.movieflix.ui.dashboard.movie.home.model.MoviesPagingSource
import com.tzel.movieflix.ui.search.model.SearchUiState
import com.tzel.movieflix.usecase.movie.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val moviesUiMapper: MovieToMovieUiMapper
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        subscribeSearchMovies()
    }

    private fun subscribeSearchMovies() {
        launch {
            snapshotFlow { uiState.value.textFieldState.text }.collectLatest { title ->
                delay(SEARCH_DELAY_TO_AVOID_MULTI_REQUESTS)

                val movies = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                    MoviesPagingSource(
                        movieToMovieUiMapper = moviesUiMapper,
                        getMovies = { page -> searchMoviesUseCase(title.toString(), page) })
                }.flow

                _uiState.update { it.copy(movies = movies) }
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val SEARCH_DELAY_TO_AVOID_MULTI_REQUESTS = 700L
    }
}