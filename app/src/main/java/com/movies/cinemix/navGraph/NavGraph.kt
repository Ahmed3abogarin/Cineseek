package com.movies.cinemix.navGraph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.movies.cinemix.presentation.favorite.FavoriteScreen
import com.movies.cinemix.presentation.favorite.SearchScreen
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.presentation.news_navigator.BottomItem
import com.movies.cinemix.presentation.news_navigator.MoviesBottomNav
import com.movies.cinemix.ui.theme.MyGreen
import com.movies.cinemix.ui.theme.MyPink

@Composable
fun NavGraph(){

    val bottomItems = remember {
        mutableStateListOf(
            BottomItem(
                icon = Icons.Rounded.Home,
                color = Color(0xFFFF0000)
            ),
            BottomItem(
                icon = Icons.Rounded.Search,
                color = MyGreen
            ),
            BottomItem(
                icon = Icons.Rounded.Favorite,
                color = MyPink
            )
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember (key1 = backstackState){
        when(backstackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.FavoriteScreen.route -> 2
            else -> 0
        }

    }

    Box (modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController,
            startDestination = Route.HomeScreen.route,
        ) {

            composable(Route.HomeScreen.route){
                HomeScreen("")
            }
            composable(Route.SearchScreen.route){
                SearchScreen()
            }
            composable(Route.FavoriteScreen.route){
                FavoriteScreen()
            }

        }

        Column(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 25.dp)) {
            MoviesBottomNav(
                bottomItems = bottomItems,
                onItemClicked = {index ->
                    when(index){
                        0 -> navigateToTab(
                            navController,
                            route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.FavoriteScreen.route
                        )
                    }
                }
            )
        }

    }





















}

fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        // every time we navigate to tab we wanna pop the backstack until we reach the home screen
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop =
                true // if you clicked multiple time on home screen icon that won't create a new instance of home screen each time
        }


    }
}