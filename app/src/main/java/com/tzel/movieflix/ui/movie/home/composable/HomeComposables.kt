package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.movie.core.MoviesPortraitLazyRow
import com.tzel.movieflix.ui.movie.home.model.HomeUiState
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.ui.search.navigation.SearchDestination
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester

@Composable
fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateTo: (NavigationDestination) -> Unit,
) {
    HomeContent(
        uiState = uiState,
        navigateTo = navigateTo
    )
}

@Composable
private fun HomeContent(
    uiState: State<HomeUiState>,
    navigateTo: (NavigationDestination) -> Unit,
) {
    val imageRequester = rememberImageRequester()
    val state = rememberLazyListState()
    val density = LocalDensity.current

    val fraction = remember() {
        derivedStateOf {
            if (state.firstVisibleItemIndex > 0) return@derivedStateOf 0f

            val itemHeight = state.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat() ?: return@derivedStateOf 0f
            val topBarHeight = with(density) { Sizes.NavigationBars.small.toPx() }
            val totalCalculatedHeight = itemHeight * 0.8f - topBarHeight

            val offset = state.firstVisibleItemScrollOffset / totalCalculatedHeight
            1f - offset.coerceIn(0f, 1f)
        }
    }

    //collect paging data outside lazy column to avoid multiple requests
    val popularMovies = uiState.value.popularCategory?.movies?.collectAsLazyPagingItems()
    val upcomingMovies = uiState.value.trendingCategory?.movies?.collectAsLazyPagingItems()
    val firstSectionGenresMovies = uiState.value.firstSectionGenres.map { genre ->
        genre.movies.collectAsLazyPagingItems()
    }
    val secondSectionGenresMovies = uiState.value.secondSectionGenres.map { genre ->
        genre.movies.collectAsLazyPagingItems()
    }

    Box {
        TrendingBackground(
            imageUrl = uiState.value.trendBackground,
            isVisible = { state.firstVisibleItemIndex == 0 },
            alpha = { fraction.value },
            modifier = Modifier.fillMaxSize(),
            imageRequester = imageRequester
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state
        ) {
            uiState.value.trendMovie?.let {
                item(key = "trend_movie") {
                    Spacer(
                        modifier = Modifier
                            .statusBarsPadding()
                            .height(Sizes.NavigationBars.small)
                    )

                    TrendMovieOfTheDay(
                        movie = it,
                        imageRequest = imageRequester,
                        addToWatchList = { uiState.value.addToWatchlist(it) },
                        onClick = { navigateTo(MovieDetailsDestination(it.id)) }
                    )
                }
            }

            item(key = "popular_movies") {
                uiState.value.popularCategory?.let { category ->
                    HomeSectionTitle(title = category.name)
                }
                popularMovies?.let { movies ->
                    PopularMovies(
                        movies = movies,
                        navigateToMovieDetails = { navigateTo(MovieDetailsDestination(it)) },
                        imageRequester = imageRequester,
                    )
                }
            }

            uiState.value.firstSectionGenres.forEachIndexed { index, category ->
                item(
                    key = "genre_movies_$index",
                    contentType = "genre_movies"
                ) {
                    HomeSectionTitle(title = category.name)
                    MoviesPortraitLazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.2f),
                        movies = firstSectionGenresMovies[index],
                        navigateToMovieDetails = { navigateTo(MovieDetailsDestination(it)) },
                        imageRequester = imageRequester,
                    )
                }
            }

            item(key = "trending_movies") {
                uiState.value.trendingCategory?.let { category ->
                    HomeSectionTitle(title = category.name)
                }
                upcomingMovies?.let { movies ->
                    MoviesPortraitLazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.2f),
                        movies = movies,
                        itemWidthPer = 0.44f,
                        navigateToMovieDetails = { navigateTo(MovieDetailsDestination(it)) },
                        imageRequester = imageRequester,
                    )
                }
            }

            uiState.value.secondSectionGenres.forEachIndexed { index, category ->
                item(
                    key = "second_section_genre_movies_$index",
                    contentType = "genre_movies"
                ) {
                    HomeSectionTitle(title = category.name)
                    MoviesPortraitLazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.2f),
                        movies = secondSectionGenresMovies[index],
                        navigateToMovieDetails = { navigateTo(MovieDetailsDestination(it)) },
                        imageRequester = imageRequester,
                    )
                }
            }

            item("bottom_spacing") {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = Spacing_32dp)
                )
            }
        }

        HomeTopBar(
            alpha = { 1 - fraction.value },
            onSearchClick = { navigateTo(SearchDestination) }
        )
    }
}

@Preview
@Composable
private fun HomePreview() {
    val uiState = remember {
        mutableStateOf(
            HomeUiState(
                popularCategory = null,
                trendingCategory = null,
                trendMovie = null,
                genreMovies = emptyList(),
                addToWatchlist = {},
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