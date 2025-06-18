package com.movies.cinemix.presentation.home

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val nowPlaying: Flow<PagingData<Movie>>? = null,
    val popularMovies: Flow<PagingData<Movie>>? = null,
    val topRatedMovies: Flow<PagingData<Movie>>? = null,
    val upcomingMovies: Flow<PagingData<Movie>>? = null,
    val marvelMovies: Flow<PagingData<Movie>>? = null,
    val trendWeek: Flow<PagingData<Movie>>? = null,
    val arabicMovies: Flow<PagingData<Movie>>? = null,

    val lastMovies: List<MovieDetails>? = null,
)