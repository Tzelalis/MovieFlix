package com.tzel.movieflix.ui.dashboard.more.current

import com.tzel.movieflix.ui.core.BaseViewModel
import com.tzel.movieflix.ui.dashboard.more.current.mapper.MoreUiItemsMapper
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiEvent
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiItem
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val moreUiItemsMapper: MoreUiItemsMapper
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(
        MoreUiState(
            items = moreUiItemsMapper(),
            onEvent = MoreUiEvent(onItemClick = ::onItemClick)
        )
    )
    val uiState = _uiState.asStateFlow()

    private fun onItemClick(item: MoreUiItem) {

    }
}