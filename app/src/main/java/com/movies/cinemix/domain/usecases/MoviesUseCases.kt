package com.movies.cinemix.domain.usecases

data class MoviesUseCases (
    val getNowPlayingMovies: GetNowPlayingMovies,
    val getPopularMovies: GetPopularMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getUpcomingMovies: GetUpcomingMovies,
    val getMovieCast: GetMovieCast,
    val getMovieKey: GetMovieKey,
    val getTrendWeek: GetTrendWeek
)