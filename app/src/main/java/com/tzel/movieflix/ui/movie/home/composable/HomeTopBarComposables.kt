package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.movie.home.model.FilterUiItem
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_2dp
import com.tzel.movieflix.ui.theme.Spacing_8dp
import com.tzel.movieflix.utils.composable.modifier.clickableWithLifecycle

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    filters: List<FilterUiItem>,
    alpha: () -> Float = { 1f },
    onSearchClick: () -> Unit,
    onFilterClick: (FilterUiItem) -> Unit,
    onClearFiltersClick: () -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.background

    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(color = backgroundColor.copy(alpha = alpha().coerceAtMost(0.7f)))
            }
            .statusBarsPadding()
            .height(Sizes.NavigationBars.small),
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilterLazyRow(
            modifier = Modifier.weight(1f),
            filters = filters,
            onFilterClick = onFilterClick,
            onClearClick = onClearFiltersClick
        )

        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(Spacing_8dp)
                .clip(CircleShape)
                .clickableWithLifecycle { onSearchClick() }
                .padding(Spacing_2dp),
            painter = painterResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.home_search_content_description),
            tint = MaterialTheme.colorScheme.surface,
        )
    }
}