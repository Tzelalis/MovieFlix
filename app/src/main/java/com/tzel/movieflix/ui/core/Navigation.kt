package com.tzel.movieflix.ui.core

import androidx.navigation.NavController
import timber.log.Timber

internal fun NavController.safeNavigate(
    route: String,
    launchSingleTop: Boolean = true,
    restoreState: Boolean = false,
    popUpToRoute: String? = null,
    inclusive: Boolean = false,
    saveState: Boolean = false,
) {
    try {
        navigate(route) {
            this.launchSingleTop = launchSingleTop
            this.restoreState = restoreState
            popUpToRoute?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                    this.saveState = saveState
                }
            }
        }
        Timber.tag("singleNavigate").v(route)
    } catch (e: Exception) {
        Timber.tag("singleNavigate").e(e)
    }
}