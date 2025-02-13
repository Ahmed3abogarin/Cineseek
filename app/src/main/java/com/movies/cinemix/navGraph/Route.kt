package com.movies.cinemix.navGraph

sealed class Route(
    val route: String
) {
    data object HomeScreen: Route(route = "someScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object FavoriteScreen: Route(route = "favoriteScreen")
    data object ContactScreen: Route(route = "contactScreen")
    data object MeScreen: Route(route = "meScreen")
}