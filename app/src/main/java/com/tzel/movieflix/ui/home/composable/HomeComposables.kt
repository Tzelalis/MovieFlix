package com.tzel.movieflix.ui.home.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.home.model.HomeUiState
import com.tzel.movieflix.ui.home.model.MovieUiItem
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateToMovieDetails: (id: String) -> Unit
) {

    HomeContent(
        uiState = uiState,
        navigateToMovieDetails = navigateToMovieDetails
    )
}

@Composable
private fun HomeContent(
    uiState: State<HomeUiState>,
    navigateToMovieDetails: (id: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.statusBarsPadding())
        }

        item {
            HomeSectionTitle(titleRes = R.string.home_popular_title)
        }

        item {
            PopularMovies(
                movies = uiState.value.popularMovies,
                navigateToMovieDetails = navigateToMovieDetails
            )
        }
    }

}

@Composable
private fun PopularMovies(
    movies: Flow<PagingData<MovieUiItem>>,
    navigateToMovieDetails: (id: String) -> Unit
) {
    val imageRequester = rememberImageRequester()
    val moviesLazyItems = movies.collectAsLazyPagingItems()


    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
        contentPadding = PaddingValues(horizontal = Spacing_16dp, vertical = Spacing_4dp),
    ) {

        items(
            count = moviesLazyItems.itemCount,
            key = moviesLazyItems.itemKey { it.tag },   //use it.tag to avoid duplicate keys on different pages
            contentType = moviesLazyItems.itemContentType { "movie" }
        ) { index ->
            moviesLazyItems[index]?.let { movie ->
                MovieItem(
                    modifier = Modifier.fillParentMaxWidth(0.8f),
                    movie = movie,
                    imageRequester = imageRequester,
                    onMovieClick = { navigateToMovieDetails(movie.id) }
                )
            }
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movie: MovieUiItem,
    imageRequester: ImageRequest.Builder,
    onMovieClick: () -> Unit
) {
    Column(modifier = modifier
        .aspectRatio(1.7f)
        .clip(MaterialTheme.shapes.large)
        .clickable { onMovieClick() }
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageRequester.data(movie.backdropPath).build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.5f to Color.Black.copy(alpha = 0.8f),
                            1f to Color.Black
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                movie.title?.let { title ->
                    Text(
                        modifier = Modifier.padding(horizontal = Spacing_16dp),
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing_8dp, horizontal = Spacing_16dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing_8dp)
        ) {
            movie.releaseDateFormatted?.let { date ->
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            FavoriteIcon(color = movie.favoriteIconColor)
        }
    }

}

@Composable
private fun FavoriteIcon(color: Color) {
    val animatedColor by animateColorAsState(targetValue = color, label = "favorite_animation")

    Icon(
        modifier = Modifier.size(24.dp),
        painter = rememberAsyncImagePainter(model = R.drawable.ic_heart),
        contentDescription = stringResource(id = R.string.home_favorite_content_description),
        tint = animatedColor
    )
}

@Preview
@Composable
private fun HomePreview() {
    val uiState = remember {
        val pager = Pager(PagingConfig(pageSize = 20)) {
            object : PagingSource<Int, MovieUiItem>() {
                override fun getRefreshKey(state: PagingState<Int, MovieUiItem>): Int? {
                    return state.anchorPosition
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieUiItem> {
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }

            }
        }.flow
        mutableStateOf(HomeUiState(pager))
    }

    MovieFlixTheme {
        HomeScreen(
            uiState = uiState,
            navigateToMovieDetails = {}
        )
    }
}