package com.movies.cinemix.presentation.navGraph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.details.DetailsScreen
import com.movies.cinemix.presentation.details.DetailsViewModel
import com.movies.cinemix.presentation.favorite.FavoriteScreen
import com.movies.cinemix.presentation.favorite.SearchScreen
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.presentation.home.HomeViewModel
import com.movies.cinemix.presentation.news_navigator.BottomItem
import com.movies.cinemix.presentation.news_navigator.MoviesBottomNav
import com.movies.cinemix.presentation.seeall.SeeAllMovies
import com.movies.cinemix.presentation.seeall.SeeAllViewModel
import com.movies.cinemix.ui.theme.MyGreen
import com.movies.cinemix.ui.theme.MyPink

@Composable
fun NavGraph() {
    val seeAllViewmodel: SeeAllViewModel = hiltViewModel()
    val detailsViewModel: DetailsViewModel = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current

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

    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.FavoriteScreen.route -> 2
            else -> 0
        }

    }


    // we want to hide the bottom navigation bar when we in the details/bookmark screen
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.FavoriteScreen.route
    }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController,
            startDestination = Route.HomeScreen.route,
        ) {

            composable(Route.HomeScreen.route) {
                val homeViewmodel: HomeViewModel = hiltViewModel()
                val moviesNow = homeViewmodel.nowPlayingMovies.collectAsLazyPagingItems()
                val popularMovies = homeViewmodel.popularMovies.collectAsLazyPagingItems()
                val topRatedMovies = homeViewmodel.topRatedMovies.collectAsLazyPagingItems()
                val upcomingMovies = homeViewmodel.upcomingMovies.collectAsLazyPagingItems()
                val trendWeek = homeViewmodel.trendWeek.collectAsLazyPagingItems()
                HomeScreen(
                    trendWeek = trendWeek,
                    moviesNow = moviesNow,
                    popularMovies = popularMovies,
                    topRatedMovies = topRatedMovies,
                    upcomingMovies = upcomingMovies,
                    navigateToAll = { category ->
                        navigateToAll(
                            navController = navController,
                            movieCategory = category
                        )
                    },
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            movie = it
                        )
                    })

            }
            composable(Route.SearchScreen.route) {
                SearchScreen()
            }
            composable(Route.FavoriteScreen.route) {
                FavoriteScreen()
            }
            composable(Route.SeeAllScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("movieCategory")
                    ?.let { category ->
                        SeeAllMovies(
                            movieCategory = category,
                            viewModel = seeAllViewmodel,
                            navigateToDetails = {
                                navigateToDetails(
                                    navController = navController,
                                    movie = it
                                )
                            }
                        )
                    }
            }

            composable(Route.DetailsScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<Movies>("movie")
                    ?.let { movie ->
                        DetailsScreen(
                            movie,
                            detailsViewModel,
                            detailsViewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            lifecycleOwner = lifecycleOwner
                        )
                    }

            }

        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 25.dp)
        ) {
            if (isBottomBarVisible) {
                MoviesBottomNav(
                    bottomItems = bottomItems,
                    onItemClicked = { index ->
                        when (index) {
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

private fun navigateToAll(navController: NavController, movieCategory: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("movieCategory", movieCategory)
    navController.navigate(
        route = Route.SeeAllScreen.route
    )

}

// Helper function to navigate to details screen
private fun navigateToDetails(navController: NavController, movie: Movies) {
    navController.currentBackStackEntry?.savedStateHandle?.set("movie", movie)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}


