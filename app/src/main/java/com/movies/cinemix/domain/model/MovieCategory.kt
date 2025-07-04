package com.movies.cinemix.domain.model

sealed class MovieCategory(val route: String, val title: String) {
    data object NowPlaying : MovieCategory("nowPlaying", "Now Playing")
    data object TopRated : MovieCategory("topRated", "Top Rated")
    data object Upcoming : MovieCategory("upcoming", "Upcoming")
    data object Popular : MovieCategory("popular", "Popular")
    data object Arabic : MovieCategory("arabic", "Arabic")
    data object Marvel : MovieCategory("marvel", "Marvel")

    open class GenreCategory(route: String, title: String, val genreId: Int) :
        MovieCategory(route, title)

    object Action : GenreCategory("Action", "Action", 28)
    object Adventure : GenreCategory("Adventure", "Adventure", 12)
    object Family : GenreCategory("Family", "Family", 10751)
    object Drama : GenreCategory("Drama", "Drama", 18)
    object Animation : GenreCategory("Animation", "Animation", 16)
    object Comedy : GenreCategory("Comedy", "Comedy", 35)
    object Romance : GenreCategory("Romance", "Romance", 10749)
    object Crime : GenreCategory("Crime", "Crime", 80)
    object Horror : GenreCategory("Horror", "Horror", 27)
    object War : GenreCategory("War", "War", 10752)
    object Fantasy : GenreCategory("Fantasy", "Fantasy", 14)

    companion object {
        fun fromRoute(route: String): MovieCategory = when (route) {
            NowPlaying.route -> NowPlaying
            TopRated.route -> TopRated
            Upcoming.route -> Upcoming
            Popular.route -> Popular
            Arabic.route -> Arabic
            Marvel.route -> Marvel
            Action.route -> Action
            Adventure.route -> Adventure
            Family.route -> Family
            Drama.route -> Drama
            Animation.route -> Animation
            Comedy.route -> Comedy
            Romance.route -> Romance
            Crime.route -> Crime
            Horror.route -> Horror
            War.route -> War
            Fantasy.route -> Fantasy
            else -> Popular
        }
    }
}
