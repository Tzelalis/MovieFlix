package com.tzel.movieflix.ui.app.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tzel.movieflix.ui.language.navigation.languageScreen
import com.tzel.movieflix.ui.movie.home.navigation.homeScreen
import com.tzel.movieflix.ui.movie.moviedetail.navigation.MovieDetailsDestination
import com.tzel.movieflix.ui.movie.moviedetail.navigation.movieDetailsScreen
import com.tzel.movieflix.ui.search.navigation.searchScreen
import com.tzel.movieflix.ui.signin.navigation.signInScreen
import com.tzel.movieflix.ui.splash.navigation.SplashDestination
import com.tzel.movieflix.ui.splash.navigation.splashScreen
import com.tzel.movieflix.utils.ext.safeNavigate

@SuppressLint("RestrictedApi")
@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = SplashDestination,
        enterTransition = { slideInHorizontally { it } },
        popExitTransition = { slideOutHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
    ) {
        splashScreen(navigateTo = { destination -> navController.safeNavigate(destination) })

        languageScreen(
            navigateTo = { destination -> navController.safeNavigate(destination) },
            navigateBack = { navController.navigateUp() },
        )

        signInScreen(navigateBack = { navController.navigateUp() })

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