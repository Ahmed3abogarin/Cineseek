package com.movies.cinemix.presentation.navGraph

import android.widget.Toast
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.CastScreen
import com.movies.cinemix.presentation.CastViewModel
import com.movies.cinemix.presentation.details.DetailsEvent
import com.movies.cinemix.presentation.details.DetailsScreen
import com.movies.cinemix.presentation.details.DetailsViewModel
import com.movies.cinemix.presentation.favorite.FavoriteScreen
import com.movies.cinemix.presentation.favorite.FavoriteViewModel
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.presentation.home.HomeViewModel
import com.movies.cinemix.presentation.news_navigator.BottomItem
import com.movies.cinemix.presentation.news_navigator.MoviesBottomNav
import com.movies.cinemix.presentation.search.SearchScreen
import com.movies.cinemix.presentation.search.SearchViewModel
import com.movies.cinemix.presentation.seeall.SeeAllMovies
import com.movies.cinemix.presentation.seeall.SeeAllViewModel
import com.movies.cinemix.ui.theme.MyGreen
import com.movies.cinemix.ui.theme.MyPink

@Composable
fun NavGraph() {
    val seeAllViewmodel: SeeAllViewModel = hiltViewModel()
    val detailsViewModel: DetailsViewModel = hiltViewModel()
    val homeViewmodel: HomeViewModel = hiltViewModel()


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
    var selectedItem by remember {
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
                val state = homeViewmodel.state.value

                HomeScreen(
                    state = state,
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
                val viewmodel: SearchViewModel = hiltViewModel()
                val state = viewmodel.state.value
                SearchScreen(
                    state = state,
                    event = viewmodel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            movie = it
                        )
                    }
                )
            }
            composable(Route.FavoriteScreen.route) {
                val viewmodel: FavoriteViewModel = hiltViewModel()
                val state = viewmodel.state.value
                FavoriteScreen(state, onClick = {
                    navigateToDetails(
                        navController = navController,
                        movie = it
                    )
                })
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
                            },
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }

            composable(Route.DetailsScreen.route) {
                if (detailsViewModel.sideEffect != null) {
                    Toast.makeText(
                        LocalContext.current,
                        detailsViewModel.sideEffect,
                        Toast.LENGTH_SHORT
                    ).show()
                    detailsViewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }

                navController.previousBackStackEntry?.savedStateHandle?.get<Movies>("movie")
                    ?.let { movie ->
                        DetailsScreen(
                            movie,
                            detailsViewModel,
                            detailsViewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            lifecycleOwner = lifecycleOwner,
                            navigateToCastDetails = {
                                navigateToCastDetails(
                                    navigator = navController,
                                    personId = it
                                )
                            }
                        )
                    }

            }
            composable(Route.CastDetailsScreen.route) {
                val castViewModel: CastViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("person_id")
                    ?.let { personId ->
                        CastScreen(
                            personId,
                            state = castViewModel.state.value,
                            event = castViewModel::onEvent, navigateUp = {navController.navigateUp()}
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
                    selectedIndex = selectedItem,
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
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}

private fun navigateToAll(navController: NavController, movieCategory: String) {
    val currentDestination = navController.currentDestination?.route
    if (currentDestination != Route.SeeAllScreen.route) {  // Prevent duplicate navigation
        navController.currentBackStackEntry?.savedStateHandle?.set("movieCategory", movieCategory)
        navController.navigate(Route.SeeAllScreen.route)
    }
}

// Helper function to navigate to details screen
private fun navigateToDetails(navController: NavController, movie: Movies) {
    navController.currentBackStackEntry?.savedStateHandle?.set("movie", movie)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}

private fun navigateToCastDetails(navigator: NavController, personId: Int) {
    navigator.currentBackStackEntry?.savedStateHandle?.set("person_id", personId)
    navigator.navigate(
        route = Route.CastDetailsScreen.route
    )
}


