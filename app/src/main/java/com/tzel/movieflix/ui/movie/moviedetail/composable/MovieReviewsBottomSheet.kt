package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieReviewsUiState
import com.tzel.movieflix.ui.movie.moviedetail.model.ReviewUi
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesReviewsBottomSheet(
    uiState: State<MovieReviewsUiState>,
    isExpanded: () -> Boolean,
    modifier: Modifier = Modifier,
    state: SheetState = rememberModalBottomSheetState()
) {
    val coroutineScope = rememberCoroutineScope()
    MoviesReviewsSideEffects(
        isVisible = isExpanded,
    ) { expanded ->
        coroutineScope.launch {
            when (expanded) {
                true -> state.show()
                false -> state.hide()
            }
        }
    }

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = { }) {

        MoviesReviewsContent(
            uiState = uiState,
            modifier = modifier
        )

    }
}

@Composable
fun MoviesReviewsContent(
    uiState: State<MovieReviewsUiState>,
    modifier: Modifier = Modifier
) {
    when (val state = uiState.value) {
        is MovieReviewsUiState.Error -> Unit
        MovieReviewsUiState.Loading -> Unit
        is MovieReviewsUiState.Success -> MoviesReviewsDefault(reviews = state.reviews)
    }


}

@Composable
fun MoviesReviewsDefault(reviews: List<ReviewUi>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing_16dp),
        contentPadding = PaddingValues(Spacing_16dp)
    ) {
        items(items = reviews) { review ->
            MovieReviewItem(review = review)
        }
    }
}

@Composable
fun MovieReviewItem(review: ReviewUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onSurface, MaterialTheme.shapes.medium)
            .padding(Spacing_8dp),
        verticalArrangement = Arrangement.spacedBy(Spacing_4dp),
    ) {
        Text(
            text = review.author,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = review.content,
            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onTertiary,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun MoviesReviewsSideEffects(
    isVisible: () -> Boolean,
    onVisibilityChanged: (Boolean) -> Unit
) {
    LaunchedEffect(key1 = isVisible()) {
        onVisibilityChanged(isVisible())
    }
}