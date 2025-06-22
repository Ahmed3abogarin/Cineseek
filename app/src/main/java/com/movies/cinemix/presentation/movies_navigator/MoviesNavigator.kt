package com.movies.cinemix.presentation.movies_navigator

import android.widget.Toast
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.movies.cinemix.R
import com.movies.cinemix.presentation.castdetails.CastScreen
import com.movies.cinemix.presentation.castdetails.CastViewModel
import com.movies.cinemix.presentation.details.DetailsEvent
import com.movies.cinemix.presentation.details.DetailsScreen
import com.movies.cinemix.presentation.details.DetailsViewModel
import com.movies.cinemix.presentation.favorite.FavoriteScreen
import com.movies.cinemix.presentation.favorite.FavoriteViewModel
import com.movies.cinemix.presentation.home.HomeScreen
import com.movies.cinemix.presentation.home.HomeViewModel
import com.movies.cinemix.presentation.movies_navigator.components.BottomItem
import com.movies.cinemix.presentation.movies_navigator.components.MoviesBottomNav
import com.movies.cinemix.presentation.navGraph.Route
import com.movies.cinemix.presentation.random.MoviePickerScreen
import com.movies.cinemix.presentation.random.PickerViewModel
import com.movies.cinemix.presentation.random.RandomMovieScreen
import com.movies.cinemix.presentation.search.SearchScreen
import com.movies.cinemix.presentation.search.SearchViewModel
import com.movies.cinemix.presentation.seeall.SeeAllMovies
import com.movies.cinemix.presentation.seeall.SeeAllViewModel
import com.movies.cinemix.ui.theme.MyGreen
import com.movies.cinemix.ui.theme.MyPink

@Composable
fun MoviesNavigatorScreen() {

    val castViewModel: CastViewModel = hiltViewModel()


    val icRandom = ImageVector.vectorResource(R.drawable.ic_random)

    val bottomItems = remember {
        mutableStateListOf(
            BottomItem(
                icon = Icons.Rounded.Home,
                title = "Home",
                color = Color(0xFFFF0000)
            ),
            BottomItem(
                icon = icRandom,
                title = "Random",
                color = Color(0xFFFF0000)
            ),
            BottomItem(
                icon = Icons.Rounded.Search,
                title = "Search",
                color = MyGreen
            ),
            BottomItem(
                icon = Icons.Rounded.Favorite,
                title = "Favorite",
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
            Route.RandomMovieScreen.route -> 1
            Route.MoviePickerScreen.route -> 1
            Route.SearchScreen.route -> 2
            Route.FavoriteScreen.route -> 3
            else -> selectedItem
        }

    }


    // we want to hide the bottom navigation bar when we in the details/bookmark screen
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.FavoriteScreen.route ||
                backstackState?.destination?.route == Route.RandomMovieScreen.route
    }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            startDestination = Route.RandomMovieScreen.route,
        ) {
            composable(Route.HomeScreen.route) {
                val homeViewmodel: HomeViewModel = hiltViewModel()
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
                            movieId = it
                        )
                    },
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    }
                )
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
                            movieId = it
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
                        movieId = it
                    )
                })
            }
            composable("seeAllScreen/{movie_category}") {
                val seeAllViewmodel: SeeAllViewModel = hiltViewModel()
                SeeAllMovies(
                    viewModel = seeAllViewmodel,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            movieId = it
                        )
                    },
                    navigateUp = { navController.navigateUp() }
                )
            }
            composable(
                "${Route.DetailsScreen.route}/{movie_id}",
//                popEnterTransition = {
//                    scaleIn() + expandVertically(
//                        expandFrom = Alignment.CenterVertically,
//                        animationSpec = tween(1000)
//                    )
//                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { x -> -x }, // Back-enter from left
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                enterTransition = {
                    scaleIn() + expandVertically(
                        expandFrom = Alignment.CenterVertically,
                        animationSpec = tween(1000)
                    )
                }) {
                val detailsViewModel: DetailsViewModel = hiltViewModel()

                if (detailsViewModel.sideEffect != null) {
                    Toast.makeText(
                        LocalContext.current,
                        detailsViewModel.sideEffect,
                        Toast.LENGTH_SHORT
                    ).show()
                    detailsViewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }

                DetailsScreen(
                    state = detailsViewModel.state.value,
                    event = detailsViewModel::onEvent,
                    navigateUp = {
                        navController.popBackStack()

                    },
                    navigateToCastDetails = {
                        navigateToCastDetails(
                            navigator = navController,
                            personId = it
                        )
                    }
                )

            }
            composable(Route.CastDetailsScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("person_id")
                    ?.let { personId ->
                        CastScreen(
                            personId = personId,
                            state = castViewModel.state.value,
                            event = castViewModel::onEvent,
                            navigateUp = {
                                navController.navigateUp()
                                navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("person_id")
                            },
                            navigateToDetails = {
                                navigateToDetails(
                                    navController = navController,
                                    movieId = it
                                )
                            }
                        )
                    }
            }

            composable(Route.MoviePickerScreen.route) {
                val viewmodel: PickerViewModel = hiltViewModel()
                MoviePickerScreen(
                    viewmodel.state.value,
//                    viewmodel,
                    navigateUp = {navController.navigateUp()},
                    navigateToDetails = { movieId -> navigateToDetails(navController, movieId) })
            }

            composable(Route.RandomMovieScreen.route) {
                RandomMovieScreen {
                    navController.navigate(Route.MoviePickerScreen.route)
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 22.dp)
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
                                navController,
                                route = Route.RandomMovieScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            3 -> navigateToTab(
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
    navController.navigate("seeAllScreen/$movieCategory") {
        popUpTo(Route.SeeAllScreen.route)
    }
}

// Helper function to navigate to details screen
private fun navigateToDetails(navController: NavController, movieId: Int) {
    navController.navigate(route = "${Route.DetailsScreen.route}/$movieId")
}

private fun navigateToCastDetails(navigator: NavController, personId: Int) {
    navigator.currentBackStackEntry?.savedStateHandle?.set("person_id", personId)
    navigator.navigate(
        route = Route.CastDetailsScreen.route
    )
}

