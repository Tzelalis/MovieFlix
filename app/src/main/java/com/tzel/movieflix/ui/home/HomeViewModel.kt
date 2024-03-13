package com.tzel.movieflix.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.home.model.HomeUiState
import com.tzel.movieflix.ui.home.model.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesPagingSource: MoviesPagingSource
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            popularMovies = Pager(PagingConfig(pageSize = PAGE_SIZE)) { moviesPagingSource }.flow
        )
    )
    val uiState = _uiState.asStateFlow()

    companion object {
        private const val PAGE_SIZE = 20
    }
}