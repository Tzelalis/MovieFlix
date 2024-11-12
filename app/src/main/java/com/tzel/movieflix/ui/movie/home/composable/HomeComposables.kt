package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.tzel.movieflix.ui.core.composable.genericPlaceholderHighlight
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.movie.core.MoviesPortraitLazyRow
import com.tzel.movieflix.ui.movie.home.model.HomeUiState
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.ui.search.navigation.SearchDestination
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.clickableWithLifecycle
import gr.opap.utils.composable.modifier.placeholder.placeholder

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    HomeContent(
        uiState = uiState,
        navigateTo = navigateTo
    )
}

@Composable
private fun HomeContent(
    uiState: State<HomeUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    val imageRequester = rememberImageRequester()
    val state = rememberLazyListState()

    //collect paging data outside lazy column to avoid multiple requests
    val popularMovies = uiState.value.popularCategory?.movies?.collectAsLazyPagingItems()
    val genresMovies = uiState.value.genreMovies.map { genre ->
        genre.movies.collectAsLazyPagingItems()
    }



    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(Sizes.NavigationBars.small)
                )
            }

            item {
                uiState.value.popularCategory?.let { category ->
                    HomeSectionTitle(title = category.name)
                }
                popularMovies?.let {
                    PopularMovies(
                        movies = popularMovies,
                        navigateToMovieDetails = { navigateTo(MovieDetailsDestination(it)) },
                        imageRequester = imageRequester,
                    )
                }
            }

            uiState.value.genreMovies.forEachIndexed { index, category ->
                item("genre_movies_$index") {
                    HomeSectionTitle(title = category.name)
                    MoviesPortraitLazyRow(
                        movies = genresMovies[index],
                        navigateToMovieDetails = { navigateTo(MovieDetailsDestination(it)) },
                        imageRequester = imageRequester,
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = Spacing_32dp)
                )
            }
        }

        HomeTopBar(
            onSearchClick = { navigateTo(SearchDestination) }
        )
    }
}

@Composable
private fun PopularMovies(
    movies: LazyPagingItems<MovieUiItem>,
    state: LazyListState = rememberLazyListState(),
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
    navigateToMovieDetails: (id: String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f),
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
                PopularMovieItem(
                    modifier = Modifier.fillParentMaxWidth(0.7f),
                    movie = movie,
                    imageRequester = imageRequester,
                    onMovieClick = dropUnlessResumed { navigateToMovieDetails(movie.id) },
                )
            }
        }
    }
}

@Composable
private fun PopularMovieItem(
    modifier: Modifier = Modifier,
    movie: MovieUiItem,
    imageRequester: ImageRequest.Builder,
    onMovieClick: () -> Unit
) {
    val isLoading = remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.large)
            .placeholder(
                visible = { isLoading.value },
                highlight = genericPlaceholderHighlight,
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
            .clickableWithLifecycle { onMovieClick() }
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
            navigateTo = {}
        )
    }
}