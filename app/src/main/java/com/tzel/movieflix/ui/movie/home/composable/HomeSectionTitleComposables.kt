package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.ui.core.composable.StringResource
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp


@Composable
fun HomeSectionTitle(
    title: StringResource,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(start = Spacing_16dp, end = Spacing_16dp, top = Spacing_32dp, bottom = Spacing_4dp),
        text = title.build(),
        style = MaterialTheme.typography.headlineMedium
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionTitlePreview() {
    MovieFlixTheme {
        HomeSectionTitle(title = StringResource.Text("Popular"))
    }
}