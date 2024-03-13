package com.tzel.movieflix.ui.home.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_8dp


@Composable
fun HomeSectionTitle(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(start = Spacing_16dp, end = Spacing_16dp, top = Spacing_8dp),
        text = stringResource(id = titleRes),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionTitlePreview() {
    MovieFlixTheme {
        HomeSectionTitle(titleRes = R.string.home_popular_title)
    }
}