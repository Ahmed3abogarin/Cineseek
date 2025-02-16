package com.movies.cinemix.presentation.navGraph

sealed class Route(
    val route: String
) {
    data object HomeScreen: Route(route = "someScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object FavoriteScreen: Route(route = "favoriteScreen")
    data object SeeAllScreen: Route(route = "seeAllScreen")
    data object DetailsScreen: Route(route = "detailsScreen")
}