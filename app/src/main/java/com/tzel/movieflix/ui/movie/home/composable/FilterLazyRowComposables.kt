package com.tzel.movieflix.ui.movie.home.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.movie.home.model.FilterUiItem
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_4dp
import com.tzel.movieflix.ui.theme.Spacing_8dp

@Composable
fun FilterLazyRow(
    modifier: Modifier = Modifier,
    filters: List<FilterUiItem>,
    onFilterClick: (FilterUiItem) -> Unit,
    onClearClick: () -> Unit
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing_8dp),
        contentPadding = PaddingValues(horizontal = Spacing_16dp, vertical = Spacing_8dp),
    ) {
        if (filters.any { it.isSelected }) {
            item(key = "clear") {
                Icon(
                    modifier = Modifier
                        .size(Sizes.Icons.medium)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                        .clickable { onClearClick() }
                        .padding(Spacing_4dp)
                        .animateItem(),
                    painter = painterResource(id = R.drawable.ic_round_close),
                    contentDescription = stringResource(id = R.string.home_filter_clear_content_description)
                )
            }
        }

        items(
            items = filters,
            key = { item -> item.id },
        ) { filter ->
            filter.Build(
                modifier = Modifier.animateItem(),
                onFilterClick = { onFilterClick(filter) }
            )
        }
    }
}