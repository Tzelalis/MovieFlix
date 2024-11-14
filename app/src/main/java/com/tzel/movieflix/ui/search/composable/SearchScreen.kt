package com.tzel.movieflix.ui.search.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.MFTextField
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.movie.core.Headline
import com.tzel.movieflix.ui.movie.core.MoviePortraitItem
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.ui.search.model.SearchUiState
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester

@Composable
fun SearchScreen(
    uiState: State<SearchUiState>,
    onBackClick: () -> Unit,
    onMovieClick: (NavigationDestination) -> Unit,
) {
    SearchContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick
    )
}

@Composable
private fun SearchContent(
    uiState: State<SearchUiState>,
    onMovieClick: (NavigationDestination) -> Unit,
    onBackClick: () -> Unit,
) {
    val movies = uiState.value.movies?.collectAsLazyPagingItems()
    val imageRequester = rememberImageRequester()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
    ) {
        Headline(
            title = TextBuilder.StringResource(R.string.search_title),
            onBackClick = onBackClick
        )

        MFTextField(
            modifier = Modifier.fillMaxWidth(),
            textFieldState = uiState.value.textFieldState,
            iconRes = R.drawable.ic_search,
            placeholder = stringResource(R.string.search_textfield_placeholder),
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(100.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing_8dp),
            horizontalArrangement = Arrangement.spacedBy(Spacing_8dp)
        ) {
            movies?.let {
                items(count = movies.itemCount) {
                    movies[it]?.let { movie ->
                        MoviePortraitItem(
                            modifier = Modifier.aspectRatio(0.7f),
                            movie = movie,
                            imageRequester = imageRequester,
                            clip = RectangleShape,
                            onMovieClick = {
                                onMovieClick(MovieDetailsDestination(movie.id))
                            }
                        )
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(top = Spacing_16dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchPreview() {
    val uiState = remember {
        mutableStateOf(SearchUiState(movies = null))
    }

    SearchScreen(
        uiState = uiState,
        onBackClick = { },
        onMovieClick = { }
    )
}