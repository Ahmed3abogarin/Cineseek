package com.movies.cinemix.navGraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.movies.cinemix.presentation.favorite.ContactScreen
import com.movies.cinemix.presentation.favorite.FavoriteScreen
import com.movies.cinemix.presentation.favorite.MeScreen
import com.movies.cinemix.presentation.favorite.SearchScreen
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.presentation.news_navigator.BottomItem
import com.movies.cinemix.presentation.news_navigator.MoviesBottomNav

@Composable
fun NavGraph(){

    val bottomItems = remember {
        mutableStateListOf(
            BottomItem(
                icon = Icons.Rounded.Home,
                color = Color(0xFF00AA44)
            ),
            BottomItem(
                icon = Icons.Rounded.ShoppingCart,
                color = Color(0xFF220055)
            ),
            BottomItem(
                icon = Icons.Rounded.Person,
                color = Color(0xFF654321)
            ),
            BottomItem(
                icon = Icons.Rounded.Search,
                color = Color(0xFFff1554)
            ),
            BottomItem(
                icon = Icons.Rounded.Call,
                color = Color(0xFF123456)
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
            Route.ContactScreen.route -> 3
            Route.MeScreen.route -> 4
            else -> 0
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
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

                        3 -> navigateToTab(
                            navController = navController,
                            route = Route.ContactScreen.route
                        )

                        4 -> navigateToTab(
                            navController = navController,
                            route =  Route.MeScreen.route
                        )
                    }
                }
            )
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
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
            composable(Route.ContactScreen.route){
                ContactScreen()
            }
            composable(Route.MeScreen.route){
                MeScreen()
            }
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