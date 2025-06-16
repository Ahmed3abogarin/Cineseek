package com.movies.cinemix.domain.model

sealed class MovieCategory(val route: String, val title: String) {
    object NowPlaying : MovieCategory("nowPlaying", "Now Playing")
    object TopRated : MovieCategory("topRated", "Top Rated")
    object Upcoming : MovieCategory("upcoming", "Upcoming")
    object Popular : MovieCategory("popular", "Popular")
    object Arabic : MovieCategory("arabic", "Arabic")
    object Marvel : MovieCategory("marvel", "Marvel Movies")

    open class GenreCategory(route: String, title: String, val genreId: Int) :
        MovieCategory(route, title)

    object Action : GenreCategory("Action", "Action Movies", 28)
    object Adventure : GenreCategory("Adventure", "Adventure Movies", 12)
    object Family : GenreCategory("Family", "Family Movies", 10751)
    object Drama : GenreCategory("Drama", "Drama Movies", 18)
    object Animation : GenreCategory("Animation", "Animation Movies", 16)
    object Comedy : GenreCategory("Comedy", "Comedy Movies", 35)
    object Romance : GenreCategory("Romance", "Romance Movies", 10749)
    object Crime : GenreCategory("Crime", "Crime Movies", 80)
    object Horror : GenreCategory("Horror", "Horror", 27)
    object War : GenreCategory("War", "War Movies", 10752)
    object Fantasy : GenreCategory("Fantasy", "Fantasy Movies", 14)

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
