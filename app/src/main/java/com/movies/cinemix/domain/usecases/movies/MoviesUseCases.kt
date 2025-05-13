package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.domain.usecases.GetLastMovies
import com.movies.cinemix.domain.usecases.GetMovieById
import com.movies.cinemix.domain.usecases.GetRandomMovie
import com.movies.cinemix.domain.usecases.UpsertLastMovie

data class MoviesUseCases (
    val getNowPlayingMovies: GetNowPlayingMovies,
    val getPopularMovies: GetPopularMovies,
    val getTopRatedMovies: GetTopRatedMovies,
    val getUpcomingMovies: GetUpcomingMovies,
    val getArabicMovies: GetArabicMovies,
    val getMovieCast: GetMovieCast,
    val getMovieKey: GetMovieKey,
    val getTrendWeek: GetTrendWeek,
    val getGenreMovies: GetGenreMovies,
    val getMarvelMovies: GetMarvelMovies,

    val searchMovie: SearchMovie,

    val upsertMovie: UpsertMovie,
    val deleteMovie: DeleteMovie,
    val getMovies: GetMovies,
    val getMovie: GetMovie,

    val getPersonInfo: GetPersonInfo,
    val getPersonMovies: GetPersonMovies,

    val upsertLastMovie: UpsertLastMovie,
    val getLastMovies: GetLastMovies,

    val getMovieById: GetMovieById,

    val getRandomMovie: GetRandomMovie
)