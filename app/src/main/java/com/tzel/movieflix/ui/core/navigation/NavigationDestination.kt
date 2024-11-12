package com.tzel.movieflix.ui.core.navigation

import androidx.navigation.NavOptionsBuilder

open class NavigationDestination {
    open val builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true }
}