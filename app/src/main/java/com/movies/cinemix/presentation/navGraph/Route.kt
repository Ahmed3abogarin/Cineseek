package com.movies.cinemix.presentation.navGraph

sealed class Route(
    val route: String,
) {
    data object HomeScreen : Route(route = "someScreen")
    data object SearchScreen : Route(route = "searchScreen")
    data object FavoriteScreen : Route(route = "favoriteScreen")
    data object SeeAllScreen : Route(route = "seeAllScreen/{movie_category}")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object CastDetailsScreen : Route(route = "castDetailsScreen")
    data object MoviesNavigation : Route(route = "moviesNavigation")
    data object MoviesNavigatorScreen: Route(route = "moviesNavigationScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object OnBoardingScreen: Route(route = "onBoardingScreen")

    data object RandomMovieScreen: Route(route = "randomMovieScreen")
    data object MoviePickerScreen: Route(route = "moviePickerScreen")

}