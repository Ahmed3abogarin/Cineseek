package com.movies.cinemix.navGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movies.cinemix.presentation.favorite.ContactScreen
import com.movies.cinemix.presentation.favorite.FavoriteScreen
import com.movies.cinemix.presentation.favorite.MeScreen
import com.movies.cinemix.presentation.favorite.SearchScreen
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.presentation.news_navigator.MoviesBottomNav

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MoviesBottomNav() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Route.HomeScreen.route){ HomeScreen("") }
            composable(route = Route.SearchScreen.route){ SearchScreen() }
            composable(route = Route.FavoriteScreen.route){ FavoriteScreen() }
            composable(route = Route.ContactScreen.route){ ContactScreen() }
            composable(route = Route.MeScreen.route){ MeScreen() }

        }
    }

}