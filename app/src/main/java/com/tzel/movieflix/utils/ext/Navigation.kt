package com.tzel.movieflix.utils.ext

import androidx.navigation.NavController
import com.tzel.movieflix.ui.core.navigation.NavigationDestination
import timber.log.Timber

internal fun NavController.safeNavigate(destination: NavigationDestination) {
    try {
        navigate(route = destination, builder = destination.builder)
        Timber.tag("safeNavigate").v(destination.toString())
    } catch (e: Exception) {
        Timber.tag("safeNavigate").e(e)
    }
}