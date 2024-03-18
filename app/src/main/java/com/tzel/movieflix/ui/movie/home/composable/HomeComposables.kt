package com.tzel.movieflix.ui.movie.home.composable

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tzel.movieflix.ui.core.composable.StatusBarBackground
import com.tzel.movieflix.ui.core.composable.genericPlaceholderHighlight
import com.tzel.movieflix.ui.movie.core.MoviesPortraitLazyRow
import com.tzel.movieflix.ui.movie.home.model.HomeUiState
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import gr.opap.utils.composable.modifier.placeholder.placeholder
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateToMovieDetails: (id: String) -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    if (refreshState.isRefreshing) {
        LaunchedEffect(true) {
            uiState.value.onRefreshClick()
        }
    }

    LaunchedEffect(uiState.value.popularCategory, uiState.value.genreMovies) {
        if (uiState.value.popularCategory != null || uiState.value.genreMovies.isNotEmpty()) {
            refreshState.endRefresh()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(refreshState.nestedScrollConnection)
    ) {
        HomeContent(
            uiState = uiState,
            navigateToMovieDetails = navigateToMovieDetails
        )
    }

    PullToRefreshContainer(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        state = refreshState
    )
}

@Composable
private fun HomeContent(
    uiState: State<HomeUiState>,
    navigateToMovieDetails: (id: String) -> Unit
) {
    val imageRequester = rememberImageRequester()
    val state = rememberScrollState()
    val states = mutableListOf<LazyListState>()
    uiState.value.genreMovies.forEach { _ ->
        states.add(rememberLazyListState())
    }

    /*
        LazyColumn with multiple horizontal LazyRow pagination has issue.
        Request same pages on vertical scroll!
        */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state),
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())

        uiState.value.popularCategory?.let { popularCategory ->
            HomeSectionTitle(title = popularCategory.name)
            PopularMovies(
                movies = popularCategory.movies,
                navigateToMovieDetails = { navigateToMovieDetails(it) },
                imageRequester = imageRequester,
            )
        }

        uiState.value.genreMovies.forEachIndexed { index, moviesUiCategory ->
            val category = uiState.value.genreMovies[index]
            key("genre_movies_$index") {
                HomeSectionTitle(title = category.name)
                MoviesPortraitLazyRow(
                    movies = category.movies,
                    state = states[index],
                    navigateToMovieDetails = { navigateToMovieDetails(it) },
                    imageRequester = imageRequester,
                )
            }

        }

        Spacer(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = Spacing_32dp)
        )
    }

    StatusBarBackground(state = state)
}

@Composable
private fun PopularMovies(
    movies: Flow<PagingData<MovieUiItem>>,
    state: LazyListState = rememberLazyListState(),
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
    navigateToMovieDetails: (id: String) -> Unit,
) {
    val moviesLazyItems = movies.collectAsLazyPagingItems()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f),
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
                MovieItem(
                    modifier = Modifier.fillParentMaxWidth(0.7f),
                    movie = movie,
                    imageRequester = imageRequester,
                    onMovieClick = { navigateToMovieDetails(movie.id) },
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
    val isLoading = remember { mutableStateOf(true) }

    Column(modifier = modifier
        .fillMaxHeight()
        .clip(MaterialTheme.shapes.large)
        .placeholder(
            visible = { isLoading.value },
            highlight = genericPlaceholderHighlight,
            color = MaterialTheme.colorScheme.surface
        )
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
                contentScale = ContentScale.Crop,
                onLoading = { isLoading.value = true },
                onSuccess = { isLoading.value = false },
                onError = { isLoading.value = false }
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
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    val uiState = remember {
        mutableStateOf(
            HomeUiState(
                popularCategory = null,
                genreMovies = emptyList(),
                onRefreshClick = {},
            )
        )
    }

    MovieFlixTheme {
        HomeScreen(
            uiState = uiState,
            navigateToMovieDetails = {}
        )
    }
}