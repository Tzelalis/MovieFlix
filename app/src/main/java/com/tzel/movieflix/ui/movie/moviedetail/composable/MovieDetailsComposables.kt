package com.tzel.movieflix.ui.movie.moviedetail.composable

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.key
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.ui.core.composable.StatusBarBackground
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUiState
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieUiStats
import com.tzel.movieflix.ui.movie.moviedetail.model.ReviewUi
import com.tzel.movieflix.ui.movie.moviedetail.model.SimilarMovieUiItem
import com.tzel.movieflix.ui.theme.BlueMedium
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.SpacingCustom_12dp
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.noRippleClickable
import com.tzel.movieflix.utils.ext.openUrlInBrowser
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieDetailsScreen(
    uiState: State<MovieDetailsUiState>,
    onBackClick: () -> Unit,
    navigateToMovie: (String) -> Unit
) {
    MovieDetailsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        navigateToMovie = navigateToMovie
    )
}

@Composable
private fun MovieDetailsContent(
    uiState: State<MovieDetailsUiState>,
    onBackClick: () -> Unit,
    navigateToMovie: (String) -> Unit
) {

    when (val state = uiState.value) {
        is MovieDetailsUiState.Success -> {
            MovieDetailsDefault(
                uiState = state,
                onBackClick = onBackClick,
                navigateToMovie = navigateToMovie
            )
        }

        MovieDetailsUiState.Error -> Unit
        MovieDetailsUiState.Loading -> Unit
    }

}

@Composable
private fun MovieDetailsDefault(
    uiState: MovieDetailsUiState.Success,
    onBackClick: () -> Unit,
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
            item {
                MovieDetailsHeader(header = stringResource(id = R.string.home_details_similar_cast_title))
            }
            item {
                CastRow(cast = uiState.movieDetails.cast)
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MovieDetailsHeader(
                    modifier = Modifier.weight(1f),
                    header = stringResource(id = R.string.home_details_reviews_title)
                )
                Text(
                    modifier = Modifier
                        .padding(start = SpacingCustom_12dp, end = SpacingCustom_12dp, top = Spacing_32dp, bottom = Spacing_8dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { }
                        .padding(horizontal = Spacing_4dp, vertical = Spacing_8dp),
                    text = stringResource(id = R.string.home_details_reviews_see_more),
                    style = MaterialTheme.typography.labelMedium,
                    color = BlueMedium,
                )
            }
        }

        item {
            ReviewsRow(reviews = uiState.movieDetails.reviews)
        }

        item {
            MovieDetailsHeader(header = stringResource(id = R.string.home_details_similar_movies_title))
        }

        item {
            SimilarMoviesRow(
                movies = uiState.similarMovies,
                onMovieClick = { movieId -> navigateToMovie(movieId) }
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
    modifier: Modifier = Modifier,
    imageRequester: ImageRequest.Builder = rememberImageRequester(),
    contentDescription: String? = null
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
            contentAlignment = Alignment.BottomEnd
        ) {
            movieUrl?.let { url ->
                Icon(
                    modifier = Modifier
                        .padding(Spacing_16dp)
                        .fillMaxWidth(0.09f)
                        .noRippleClickable { context.shareMovie(url) },
                    painter = painterResource(R.drawable.ic_square_share),
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = null,
                )
            }
        }
    }
}

private fun Context.shareMovie(url: String?) {
    if (url.isNullOrBlank()) return

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }

    if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(sendIntent)
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
private fun MovieDetailsHeader(
    header: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(start = Spacing_16dp, end = Spacing_16dp, top = Spacing_32dp, bottom = Spacing_8dp),
        text = header,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
private fun MovieStatsRow(
    stats: List<MovieUiStats>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing_16dp),
        horizontalArrangement = Arrangement.spacedBy(Spacing_8dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        stats.forEachIndexed { index, stat ->
            key(index) {
                MovieStatsItem(
                    modifier = Modifier.weight(1f),
                    icon = stat.icon,
                    label = stat.label
                )
            }
        }
    }
}

@Composable
private fun MovieStatsItem(
    @DrawableRes icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = rememberAsyncImagePainter(model = icon),
            contentDescription = contentDescription
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
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

@Composable
private fun CastRow(
    cast: List<Cast>,
    modifier: Modifier = Modifier,
    asyncImageRequest: ImageRequest.Builder = rememberImageRequester()
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
        contentPadding = PaddingValues(horizontal = Spacing_16dp)
    ) {
        items(items = cast, key = { it.id }) { actor ->
            CastItem(
                modifier = Modifier.fillParentMaxWidth(0.35f),
                actor = actor,
                asyncImageRequest = asyncImageRequest
            )
        }
    }
}

@Composable
private fun CastItem(
    actor: Cast,
    modifier: Modifier = Modifier,
    asyncImageRequest: ImageRequest.Builder
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing_8dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
                .clickable { context.openUrlInBrowser(actor.profileUrl, actor.name) },
            model = asyncImageRequest
                .data(actor.imageUrl)
                .placeholder(R.drawable.ic_user_tie)
                .error(R.drawable.ic_user_tie)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = actor.name ?: "",
            style = MaterialTheme.typography.labelMedium,
            maxLines = 2,
            minLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 1.em
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReviewsRow(reviews: List<ReviewUi>) {
    val state = rememberPagerState(pageCount = { reviews.size })
    HorizontalPager(
        state = state,
        contentPadding = PaddingValues(horizontal = Spacing_16dp),
        pageSpacing = Spacing_16dp
    ) { index ->
        ReviewItem(
            modifier = Modifier.fillMaxWidth(),
            review = reviews[index]
        )
    }
}

@Composable
private fun ReviewItem(
    review: ReviewUi,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .aspectRatio(2f)
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, MaterialTheme.colorScheme.onSurface, MaterialTheme.shapes.medium)
            .clickable(review.isClickable) { review.url?.let { context.openUrlInBrowser(url = review.url, review.author) } }
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
fun SimilarMoviesRow(
    movies: Flow<PagingData<SimilarMovieUiItem>>,
    modifier: Modifier = Modifier,
    asyncImageRequest: ImageRequest.Builder = rememberImageRequester(),
    onMovieClick: (String) -> Unit
) {
    val moviesLazyItems = movies.collectAsLazyPagingItems()

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
        contentPadding = PaddingValues(horizontal = Spacing_16dp)
    ) {
        items(
            count = moviesLazyItems.itemCount,
            key = moviesLazyItems.itemKey { it.key },
            contentType = moviesLazyItems.itemContentType { "similar_movie" }
        ) { index ->
            moviesLazyItems[index]?.let { movie ->
                SimilarMovieItem(
                    modifier = Modifier.fillParentMaxWidth(0.28f),
                    movie = movie,
                    asyncImageRequest = asyncImageRequest,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun SimilarMovieItem(
    movie: SimilarMovieUiItem,
    modifier: Modifier = Modifier,
    asyncImageRequest: ImageRequest.Builder,
    onMovieClick: (String) -> Unit
) {
    AsyncImage(
        modifier = modifier
            .aspectRatio(0.6f)
            .clip(MaterialTheme.shapes.large)
            .clickable { onMovieClick(movie.id) },
        model = asyncImageRequest.data(movie.imageUrl).build(),
        contentDescription = movie.contentDescription,
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun MovieDetailsPreview() {
    val uiState = remember {
        val pager = Pager(PagingConfig(pageSize = 20)) {
            object : PagingSource<Int, SimilarMovieUiItem>() {
                override fun getRefreshKey(state: PagingState<Int, SimilarMovieUiItem>): Int? {
                    return state.anchorPosition
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimilarMovieUiItem> {
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
                    reviews = emptyList()
                ),
                similarMovies = pager
            )
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