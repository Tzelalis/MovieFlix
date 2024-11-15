package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.MVButton
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.composable.modifier.clickableWithLifecycle
import com.tzel.movieflix.utils.ext.openYoutubeVideo

@Composable
fun TrendMovieOfTheDay(
    movie: MovieDetailsUi,
    imageRequest: ImageRequest.Builder = rememberImageRequester(),
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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

        movie.trailerVideo?.let { trailerUrl ->
            MVButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.8f)
                    .padding(Spacing_8dp),
                text = stringResource(R.string.home_details_watch_trailer_button),
                leadingIcon = painterResource(id = R.drawable.ic_play_arrow),
                onClick = { context.openYoutubeVideo(trailerUrl.key) }
            )
        }
    }
}