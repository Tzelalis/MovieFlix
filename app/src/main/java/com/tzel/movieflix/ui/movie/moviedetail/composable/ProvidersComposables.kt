package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchProviderUiItem
import com.tzel.movieflix.ui.movie.moviedetail.model.WatchUiProvider
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.image.rememberImageRequester

@Composable
fun ProvidersLazyRow(
    watchProvider: WatchUiProvider,
    modifier: Modifier = Modifier,
    onProviderClick: (WatchProviderUiItem) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing_4dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = Spacing_16dp, vertical = Spacing_8dp)
    ) {
        items(
            items = watchProvider.items,
            key = { it.providerId.toString() },
        ) { provider ->
            ProviderItem(
                watchProvider = provider,
                modifier = modifier,
                onProviderClick = { onProviderClick(provider) }
            )
        }
    }
}

@Composable
private fun ProviderItem(
    watchProvider: WatchProviderUiItem,
    modifier: Modifier = Modifier,
    onProviderClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(Spacing_4dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onProviderClick() }
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(Spacing_4dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing_8dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .heightIn(min = Sizes.Icons.medium)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium),
            model = rememberImageRequester().data(watchProvider.logoPath).build(),
            contentDescription = watchProvider.providerName
        )
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = watchProvider.providerName ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = watchProvider.providerType.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }

    }
}