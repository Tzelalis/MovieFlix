package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieDetailsUi
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester

@Composable
fun TrendMovieOfTheDay(
    movie: MovieDetailsUi,
    imageRequest: ImageRequest.Builder = rememberImageRequester(),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(Spacing_16dp)
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest.data(movie.posterUrl).build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
    }
}