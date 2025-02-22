package com.movies.cinemix.presentation.home

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movies
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val nowPlaying: Flow<PagingData<Movies>>? = null,
    val popularMovies: Flow<PagingData<Movies>>? = null,
    val topRatedMovies: Flow<PagingData<Movies>>? = null,
    val upcomingMovies: Flow<PagingData<Movies>>? = null,
    val trendWeek: Flow<PagingData<Movies>>? = null
)