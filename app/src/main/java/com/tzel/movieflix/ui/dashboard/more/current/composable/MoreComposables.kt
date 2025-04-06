package com.tzel.movieflix.ui.dashboard.more.current.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiEvent
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiItem
import com.tzel.movieflix.ui.dashboard.more.current.model.MoreUiState
import com.tzel.movieflix.ui.theme.MovieFlixTheme
import com.tzel.movieflix.ui.theme.Sizes
import com.tzel.movieflix.ui.theme.Spacing_16dp
import com.tzel.movieflix.ui.theme.Spacing_32dp
import com.tzel.movieflix.ui.theme.Spacing_4dp

@Composable
fun MoreScreen(
    uiState: State<MoreUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    MoreContent(
        uiState = uiState,
        navigateTo = navigateTo
    )
}

@Composable
private fun MoreContent(
    uiState: State<MoreUiState>,
    navigateTo: (NavigationDestination) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Spacing_4dp),
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .height(Spacing_32dp)
            )
        }

        items(items = uiState.value.items) { item ->
            MoreItem(
                item = item,
                onClick = {
                    uiState.value.onEvent.onItemClick(item)
                    item.destination?.let(navigateTo)
                },
            )
        }
    }
}

@Composable
private fun MoreItem(
    item: MoreUiItem,
    onClick: (MoreUiItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable { onClick(item) }
            .padding(Spacing_16dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing_16dp)
    ) {
        item.iconRes?.let { iconRes ->
            Icon(
                modifier = Modifier.size(Sizes.Icons.medium),
                painter = painterResource(iconRes),
                contentDescription = null
            )
        }

        Text(
            text = item.title.build(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun MorePreview() {
    val uiState = MoreUiState(
        items = listOf(
            MoreUiItem.Language,
            MoreUiItem.DashboardSettings,
            MoreUiItem.Logout
        ),
        onEvent = MoreUiEvent(
            onItemClick = {}
        )
    )

    MovieFlixTheme {
        MoreScreen(
            uiState = remember { mutableStateOf(uiState) },
            navigateTo = {}
        )
    }
}