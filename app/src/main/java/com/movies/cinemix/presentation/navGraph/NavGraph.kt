package com.movies.cinemix.presentation.navGraph


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.movies.cinemix.presentation.movies_navigator.MoviesNavigatorScreen
import com.movies.cinemix.presentation.onboarding.OnBoardingScreen
import com.movies.cinemix.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.MoviesNavigation.route,
            startDestination = Route.MoviesNavigatorScreen.route
        ) {
            composable(route = Route.MoviesNavigatorScreen.route){
                MoviesNavigatorScreen()
            }
        }
    }

}


