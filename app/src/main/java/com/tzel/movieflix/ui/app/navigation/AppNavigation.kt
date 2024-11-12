package com.tzel.movieflix.ui.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tzel.movieflix.ui.movie.home.navigation.HomeDestination
import com.tzel.movieflix.ui.movie.home.navigation.homeScreen
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.ui.movie.moviedetail.navigation.movieDetailsScreen
import com.tzel.movieflix.ui.search.navigation.searchScreen
import com.tzel.movieflix.ui.splash.navigation.SplashDestination
import com.tzel.movieflix.ui.splash.navigation.splashScreen
import com.tzel.movieflix.utils.ext.safeNavigate

@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashDestination,
    ) {
        splashScreen(navigateToDashboard = { navController.safeNavigate(HomeDestination) })

        homeScreen(navigateTo = { destination -> navController.safeNavigate(destination) })

        movieDetailsScreen(
            navigateToMovieDetails = { id -> navController.safeNavigate(MovieDetailsDestination(id)) },
            onBackClick = { navController.navigateUp() }
        )

        searchScreen(
            navigateTo = { destination -> navController.safeNavigate(destination) },
            onBackClick = { navController.navigateUp() }
        )
    }
}