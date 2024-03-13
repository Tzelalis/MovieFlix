package com.tzel.movieflix.ui.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tzel.movieflix.ui.splash.navigation.SplashRoute
import com.tzel.movieflix.ui.splash.navigation.splashScreen

@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashRoute,
    ) {
        splashScreen(navigateToDashboard = {})
    }
}