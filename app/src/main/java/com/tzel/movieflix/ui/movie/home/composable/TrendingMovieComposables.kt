package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.MVButton
import com.tzel.movieflix.ui.movie.moviedetail.composable.AddToWatchlistComposables
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.clickableWithLifecycle
import com.tzel.movieflix.utils.ext.openYoutubeVideo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrendMovieOfTheDay(
    movie: MovieDetailsUi,
    imageRequest: ImageRequest.Builder = rememberImageRequester(),
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    addToWatchList: () -> Unit,
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .padding(Spacing_16dp)
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickableWithLifecycle { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest.data(movie.posterUrl).build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(bottomStartPercent = 50))
                .padding(horizontal = Spacing_8dp, vertical = Spacing_4dp),
            text = stringResource(R.string.home_trending_label),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = Spacing_16dp, vertical = Spacing_8dp),
            verticalArrangement = Arrangement.spacedBy(Spacing_8dp)
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(Spacing_4dp)
            ) {
                movie.genres.forEachIndexed { index, genre ->
                    key(index) {
                        Text(
                            modifier = Modifier,
                            text = genre.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )

                        if (index != movie.genres.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(horizontal = Spacing_4dp)
                                    .size(Spacing_4dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing_8dp)
            ) {
                movie.trailerVideo?.let { trailerUrl ->
                    MVButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.home_trend_movie_watch_trailer_button),
                        leadingIcon = painterResource(id = R.drawable.ic_play_arrow),
                        onClick = { context.openYoutubeVideo(trailerUrl.key) }
                    )
                }
                AddToWatchlistComposables(
                    modifier = Modifier.weight(1f),
                    state = movie.watchlistUiState,
                    text = stringResource(id = R.string.home_trend_movie_watchlist_button),
                    onClick = addToWatchList
                )
            }
        }
    }
}