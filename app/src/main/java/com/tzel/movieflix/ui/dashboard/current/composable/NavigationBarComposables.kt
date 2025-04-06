package com.tzel.movieflix.ui.dashboard.current.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.tzel.movieflix.ui.dashboard.current.model.NavigationUiItem
import com.tzel.movieflix.ui.theme.Sizes

@SuppressLint("RestrictedApi")
@Composable
fun MFNavigationBar(
    modifier: Modifier = Modifier,
    items: List<NavigationUiItem>,
    currentDestination: () -> NavDestination?,
    onItemClick: (NavigationUiItem) -> Unit
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        items.forEach { navigationItem ->
            NavigationBarItem(
                selected = currentDestination()?.hierarchy?.any { it.hasRoute(navigationItem.destination::class) } == true,
                colors = NavigationBarItemDefaults.colors().copy(selectedTextColor = MaterialTheme.colorScheme.surface),
                icon = {
                    Icon(
                        modifier = Modifier.size(Sizes.Icons.small),
                        painter = painterResource(navigationItem.iconRes),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = navigationItem.label.build(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = dropUnlessResumed { onItemClick(navigationItem) }
            )
        }
    }
}