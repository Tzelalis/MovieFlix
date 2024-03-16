package com.tzel.movieflix.ui.movie.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import kotlinx.coroutines.flow.Flow

@Composable
fun MoviesPortraitLazyRow(
    movies: Flow<PagingData<MovieUiItem>>,
    state: LazyListState = rememberLazyListState(),
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
    navigateToMovieDetails: (id: String) -> Unit
) {
    val moviesLazyItems = movies.collectAsLazyPagingItems()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.2f),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
        contentPadding = PaddingValues(horizontal = Spacing_16dp, vertical = Spacing_4dp),
    ) {
        items(
            count = moviesLazyItems.itemCount,
            key = moviesLazyItems.itemKey { it.key },
            contentType = moviesLazyItems.itemContentType { "movie" }
        ) { index ->
            moviesLazyItems[index]?.let { movie ->
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
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.large)
            .clickable { onMovieClick() },
        contentAlignment = Alignment.TopEnd
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequester.data(movie.posterPath).build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
        FavoriteIcon(
            modifier = Modifier.padding(Spacing_8dp),
            color = movie.favoriteIconColor,
        )
    }
}