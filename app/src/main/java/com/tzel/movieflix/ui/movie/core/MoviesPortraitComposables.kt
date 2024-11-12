package com.tzel.movieflix.ui.movie.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.tzel.movieflix.ui.core.composable.genericPlaceholderHighlight
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import gr.opap.utils.composable.modifier.placeholder.placeholder

@Composable
fun MoviesPortraitLazyRow(
    movies: LazyPagingItems<MovieUiItem>,
    state: LazyListState = rememberLazyListState(),
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
    navigateToMovieDetails: (id: String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.2f),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
        contentPadding = PaddingValues(horizontal = Spacing_16dp, vertical = Spacing_4dp),
    ) {
        items(
            count = movies.itemCount,
            key = movies.itemKey { it.key },
            contentType = movies.itemContentType { "movie" }
        ) { index ->
            movies[index]?.let { movie ->
                MoviePortraitItem(
                    modifier = Modifier.fillParentMaxWidth(0.28f),
                    movie = movie,
                    imageRequester = imageRequester,
                    onMovieClick = { navigateToMovieDetails(movie.id) }
                )
            }
        }
    }
}

@Composable
private fun MoviePortraitItem(
    modifier: Modifier = Modifier,
    movie: MovieUiItem,
    imageRequester: ImageRequest.Builder,
    onMovieClick: () -> Unit
) {
    val isLoading = remember { mutableStateOf(true) }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.large)
            .placeholder(
                visible = { isLoading.value },
                highlight = genericPlaceholderHighlight,
                color = MaterialTheme.colorScheme.surface
            )
            .clickable { onMovieClick() },
        contentAlignment = Alignment.TopEnd
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequester.data(movie.posterPath).build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            onLoading = { isLoading.value = true },
            onSuccess = { isLoading.value = false },
            onError = { isLoading.value = false }
        )
    }
}