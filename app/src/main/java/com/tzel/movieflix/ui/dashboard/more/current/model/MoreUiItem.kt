package com.tzel.movieflix.ui.dashboard.more.current.model

import androidx.annotation.DrawableRes
import com.tzel.movieflix.R
import com.tzel.movieflix.ui.core.composable.TextBuilder
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import com.tzel.movieflix.ui.dashboard.more.dashboardsettings.navigation.DashboardSettingsDestination

sealed class MoreUiItem(
    val title: TextBuilder,
    @DrawableRes val iconRes: Int? = null,
    val destination: NavigationDestination? = null
) {
    data object Language : MoreUiItem(title = TextBuilder.StringResource(R.string.more_language))

    data object DashboardSettings : MoreUiItem(
        title = TextBuilder.StringResource(R.string.more_dashboard_settings),
        destination = DashboardSettingsDestination
    )

    data object Logout : MoreUiItem(title = TextBuilder.StringResource(R.string.more_logout))
}