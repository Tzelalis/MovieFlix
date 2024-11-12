package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.tzel.movieflix.R
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.ui.core.composable.ErrorContent
import com.tzel.movieflix.ui.core.composable.LoadingContent
import com.tzel.movieflix.ui.core.composable.StatusBarBackground
import com.tzel.movieflix.ui.movie.core.FavoriteIcon
import com.tzel.movieflix.ui.movie.core.MoviesPortraitLazyRow
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.theme.GrayLightWithAlpha
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.noRippleClickable
import com.tzel.movieflix.utils.ext.sharePlainText

@Composable
fun MovieDetailsScreen(
    uiState: State<MovieDetailsUiState>,
    onBackClick: () -> Unit,
    navigateToMovie: (String) -> Unit
) {
    MovieDetailsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        navigateToMovie = navigateToMovie,
        onRefreshClick = { uiState.value.onRefresh() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsContent(
    uiState: State<MovieDetailsUiState>,
    onBackClick: () -> Unit,
    navigateToMovie: (String) -> Unit,
    onRefreshClick: () -> Unit,
) {
    val isLoading = remember(uiState.value) {
        derivedStateOf {
            uiState.value is MovieDetailsUiState.Loading
        }
    }

    PullToRefreshBox(
        isRefreshing = isLoading.value,
        onRefresh = onRefreshClick
    ) {
        when (val state = uiState.value) {
            is MovieDetailsUiState.Success -> {
                MovieDetailsDefault(
                    uiState = state,
                    navigateToMovie = navigateToMovie
                )
            }

            is MovieDetailsUiState.Error -> ErrorContent(
                modifier = Modifier.fillMaxSize(),
                onRetry = { uiState.value.onRefresh() }
            )

            is MovieDetailsUiState.Loading -> LoadingContent(modifier = Modifier.fillMaxSize())
        }

        BackButton(
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopStart),
            onBackClick = onBackClick
        )
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .padding(Spacing_16dp)
            .size(32.dp)
            .clip(CircleShape)
            .border(1.dp, GrayLightWithAlpha, CircleShape)
            .background(GrayLightWithAlpha.copy(alpha = 0.15f), CircleShape)
            .clickable { onBackClick() }
            .padding(Spacing_8dp),
        painter = rememberAsyncImagePainter(model = R.drawable.ic_arrow_back_24),
        contentDescription = stringResource(id = R.string.back_button_content_description),
        tint = GrayLightWithAlpha
    )
}

@Composable
private fun MovieDetailsDefault(
    uiState: MovieDetailsUiState.Success,
    navigateToMovie: (String) -> Unit
) {
    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
    ) {
        item {
            MovieDetailsImage(
                modifier = Modifier.fillMaxWidth(),
                movieUrl = uiState.movieDetails.homepage,
                imageUrl = (uiState).movieDetails.imageUrl ?: "",
                favoriteColor = uiState.movieDetails.favoriteColor,
                onFavoriteClick = uiState.onFavoriteClick,
            )
        }

        item {
            MovieDetailsTitle(
                title = uiState.movieDetails.title,
                genres = uiState.movieDetails.genres
            )
        }

        item {
            MovieStatsRow(
                modifier = Modifier.padding(top = Spacing_32dp),
                stats = uiState.movieDetails.stats,
            )
        }

        item {
            MovieDetailsHeader(header = uiState.movieDetails.tagline ?: stringResource(id = R.string.home_details_description_title))
        }

        item {
            MovieOverview(overview = uiState.movieDetails.overview)
        }

        if (uiState.movieDetails.cast.isNotEmpty()) {
            item { MovieDetailsHeader(header = stringResource(id = R.string.home_details_similar_cast_title)) }
            item { CastLazyRow(cast = uiState.movieDetails.cast) }
        }

        if (uiState.movieDetails.reviews.isNotEmpty()) {
            item { ReviewHeader(onSeeMoreClick = { }) }
            item { ReviewsLazyRow(reviews = uiState.movieDetails.reviews) }
        }

        item {
            MovieDetailsHeader(header = stringResource(id = R.string.home_details_similar_movies_title))
        }

        item {
            MoviesPortraitLazyRow(
                movies = uiState.similarMovies,
                navigateToMovieDetails = { movieId -> navigateToMovie(movieId) },
            )
        }

        item {
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(Spacing_32dp)
            )
        }
    }

    StatusBarBackground(state = state)
}

@Composable
private fun MovieDetailsImage(
    imageUrl: String,
    movieUrl: String?,
    favoriteColor: Color,
    modifier: Modifier = Modifier,
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
    contentDescription: String? = null,
    onFavoriteClick: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequester.data(imageUrl).build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        Row(
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
                )
                .padding(Spacing_16dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Spacer(modifier = Modifier.weight(0.8f))
            FavoriteIcon(
                modifier = Modifier.weight(0.1f),
                color = favoriteColor,
                onClick = { onFavoriteClick() }
            )
            Spacer(modifier = Modifier.width(Spacing_16dp))
            movieUrl?.let { url ->
                Icon(
                    modifier = Modifier
                        .weight(0.1f)
                        .noRippleClickable { context.sharePlainText(url) },
                    painter = painterResource(R.drawable.ic_square_share),
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun MovieDetailsTitle(
    title: String?,
    genres: String,
) {
    Text(
        modifier = Modifier.padding(start = Spacing_16dp, end = Spacing_16dp, bottom = Spacing_8dp),
        text = title.orEmpty(),
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        modifier = Modifier.padding(horizontal = Spacing_16dp),
        text = genres,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun MovieOverview(
    overview: String?,
    modifier: Modifier = Modifier
) {
    overview?.let {
        Text(
            modifier = modifier.padding(horizontal = Spacing_16dp),
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Preview
@Composable
private fun MovieDetailsPreview() {
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

        mutableStateOf(
            MovieDetailsUiState.Success(
                movieDetails = MovieDetailsUi(
                    id = "1",
                    title = "Movie Title",
                    overview = "Movie Overview",
                    imageUrl = "Movie Backdrop Path",
                    voteAverage = 5.0,
                    voteCount = 100,
                    genres = "Action, Adventure",
                    tagline = "Movie Tagline",
                    budget = "1000000",
                    status = "Movie Status",
                    adult = false,
                    cast = listOf(
                        Cast(
                            id = "1",
                            name = "Actor Name",
                            imageUrl = "Actor Image Url"
                        ), Cast(
                            id = "2",
                            name = "Actor Name 2",
                            imageUrl = "Actor Image Url 2"
                        )
                    ),
                    popularity = 0.0,
                    stats = emptyList(),
                    homepage = "Movie Homepage",
                    reviews = emptyList(),
                    isFavorite = false
                ),
                similarMovies = pager,
                onFavoriteClick = {},
                refresh = {}
            ),
        )
    }

    MovieFlixTheme {
        MovieDetailsScreen(
            uiState = uiState,
            onBackClick = {},
            navigateToMovie = {}
        )
    }
}