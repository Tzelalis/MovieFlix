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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.tzel.movieflix.R
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.domain.movie.entity.Genre
import com.tzel.movieflix.ui.core.composable.ErrorContent
import com.tzel.movieflix.ui.core.composable.LoadingContent
import com.tzel.movieflix.ui.core.composable.MVButton
import com.tzel.movieflix.ui.core.composable.StatusBarBackground
import com.tzel.movieflix.ui.movie.core.FavoriteIcon
import com.tzel.movieflix.ui.movie.core.MoviesPortraitLazyRow
import com.tzel.movieflix.ui.movie.home.model.MovieUiItem
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchlistUiState
import com.tzel.movieflix.ui.theme.GrayLightWithAlpha
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.noRippleClickable
import com.tzel.movieflix.utils.ext.openUrlInBrowser
import com.tzel.movieflix.utils.ext.openYoutubeVideo
import com.tzel.movieflix.utils.ext.sharePlainText


@Composable
fun MovieDetailsScreen(
    uiState: State<MovieDetailsUiState>,
    onBackClick: () -> Unit,
    navigateToMovie: (String) -> Unit,
) {
    MovieDetailsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        navigateToMovie = navigateToMovie,
    )
}

@Composable
private fun MovieDetailsContent(
    uiState: State<MovieDetailsUiState>,
    onBackClick: () -> Unit,
    navigateToMovie: (String) -> Unit,
) {
    Box {
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
            modifier = Modifier.statusBarsPadding(),
            onBackClick = onBackClick
        )
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Icon(
        modifier = modifier
            .padding(Spacing_16dp)
            .size(Sizes.Icons.medium)
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
    navigateToMovie: (String) -> Unit,
) {
    val state = rememberLazyListState()
    val similarMovies = uiState.similarMovies.collectAsLazyPagingItems()
    val context = LocalContext.current

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
                genres = uiState.movieDetails.genresText
            )
        }

        item {
            uiState.movieDetails.trailerVideo?.let { trailer ->
                val context = LocalContext.current
                MVButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Spacing_16dp, end = Spacing_16dp, top = Spacing_16dp),
                    text = stringResource(id = R.string.home_details_watch_trailer_button),
                    leadingIcon = painterResource(id = R.drawable.ic_play_arrow),
                    onClick = {
                        context.openYoutubeVideo(trailer.key)
                    }
                )
            }
        }

        item {
            AddToWatchlistComposables(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Spacing_16dp, end = Spacing_16dp, bottom = Spacing_16dp),
                state = uiState.movieDetails.watchlistUiState,
                text = stringResource(id = R.string.home_details_watchlist_button),
                onClick = uiState.addToWatchlist
            )
        }

        item {
            MovieStatsRow(
                modifier = Modifier.padding(top = Spacing_16dp),
                stats = uiState.movieDetails.stats,
            )
        }

        item {
            MovieDetailsHeader(header = uiState.movieDetails.tagline ?: stringResource(id = R.string.home_details_description_title))
        }

        item {
            MovieOverview(overview = uiState.movieDetails.overview)
        }

        uiState.movieDetails.watchProviders?.let { watchProviders ->
            item {
                ProvidersLazyRow(
                    watchProvider = watchProviders,
                    isClickable = watchProviders.hasLink,
                    onProviderClick = { provider ->
                        watchProviders.link?.let { link ->
                            context.openUrlInBrowser(url = link)
                        }
                    }
                )
            }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2.2f),
                movies = similarMovies,
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
    onFavoriteClick: () -> Unit,
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
                .padding(Spacing_16dp)
                .wrapContentWidth(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
            verticalAlignment = Alignment.Bottom
        ) {
            FavoriteIcon(
                modifier = Modifier.size(Sizes.Icons.small),
                color = favoriteColor,
                onClick = { onFavoriteClick() }
            )
            movieUrl?.let { url ->
                Icon(
                    modifier = Modifier
                        .size(Sizes.Icons.small)
                        .noRippleClickable { context.sharePlainText(url) },
                    painter = painterResource(R.drawable.ic_square_share),
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = stringResource(R.string.home_details_share_movie_content_description),
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
    modifier: Modifier = Modifier,
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
                    genres = listOf(Genre("1", "Action"), Genre("2", "Action")),
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
                    posterUrl = "",
                    images = null,
                    videos = emptyList(),
                    isFavorite = false,
                    watchlistUiState = mutableStateOf(WatchlistUiState.Added),
                    watchProviders = null
                ),
                similarMovies = pager,
                onFavoriteClick = {},
                addToWatchlist = {},
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