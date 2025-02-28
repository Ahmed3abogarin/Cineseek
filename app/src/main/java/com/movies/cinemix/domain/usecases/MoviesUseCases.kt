package com.movies.cinemix.domain.usecases

data class MoviesUseCases (
    val getNowPlayingMovies: GetNowPlayingMovies,
    val getPopularMovies: GetPopularMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getUpcomingMovies: GetUpcomingMovies,
    val getArabicMovies: GetArabicMovies,
    val getMovieCast: GetMovieCast,
    val getMovieKey: GetMovieKey,
    val getTrendWeek: GetTrendWeek,
    val getNextYearMovies: GetNextYearMovies,
    val searchMovie: SearchMovie,

    val upsertMovie: UpsertMovie,
    val deleteMovie: DeleteMovie,
    val getMovies: GetMovies,
    val getMovie: GetMovie,

    val getPersonInfo: GetPersonInfo,
    val getPersonMovies: GetPersonMovies
)