package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tzel.movieflix.R
import com.tzel.movieflix.domain.movie.entity.Cast
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester
import com.tzel.movieflix.utils.ext.openUrlInBrowser

@Composable
fun CastLazyRow(
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

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CastLazyRowPreview() {
    val cast = listOf(
        Cast(
            id = "1",
            name = "Actor 1",
            imageUrl = "https://image.tmdb.org/t/p/w185/8YyJXqqg5zEw3p5A4jTPT0PmXx2.jpg",
        ),
        Cast(
            id = "2",
            name = "Actor 1",
            imageUrl = "https://image.tmdb.org/t/p/w185/8YyJXqqg5zEw3p5A4jTPT0PmXx2.jpg",
        ),
        Cast(
            id = "3",
            name = "Actor 1",
            imageUrl = "https://image.tmdb.org/t/p/w185/8YyJXqqg5zEw3p5A4jTPT0PmXx2.jpg",
        ),
    )

    MovieFlixTheme {
        CastLazyRow(cast = cast)
    }
}