package com.tzel.movieflix.ui.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tzel.movieflix.ui.home.navigation.homeScreen
import com.tzel.movieflix.ui.home.navigation.navigateToHome
import com.tzel.movieflix.ui.moviedetail.navigation.movieDetailsScreen
import com.tzel.movieflix.ui.moviedetail.navigation.navigateToMovieDetails
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
        splashScreen(navigateToDashboard = { navController.navigateToHome() })

        homeScreen(navigateToMovieDetails = { id -> navController.navigateToMovieDetails(id) })

        movieDetailsScreen(
            navigateToMovieDetails = { id -> navController.navigateToMovieDetails(id) },
            onBackClick = { navController.navigateUp() }
        )
    }
}