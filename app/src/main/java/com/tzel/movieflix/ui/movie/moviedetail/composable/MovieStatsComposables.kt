package com.tzel.movieflix.ui.movie.moviedetail.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.movie.home.composable.MovieDetailsAnimatedSection
import com.tzel.movieflix.ui.movie.moviedetail.model.MovieUiStats
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@Composable
fun MovieStatsRow(
    stats: List<MovieUiStats>,
    modifier: Modifier = Modifier
) {
    MovieDetailsAnimatedSection(visible = { stats.isNotEmpty() }) {
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
                        label = stat.label,
                        contentDescription = stat.contentDescription?.build()
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieStatsItem(
    @DrawableRes icon: Int,
    label: TextBuilder,
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
            text = label.build(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun MovieStatsRowPreview() {
    val stats = listOf(
        MovieUiStats(
            icon = R.drawable.ic_calendar,
            label = TextBuilder.Text("09 Dec 2024")
        ),
        MovieUiStats(
            icon = R.drawable.ic_clock,
            label = TextBuilder.Text("155")
        ),
    )

    MovieFlixTheme {
        MovieStatsRow(stats = stats)
    }
}