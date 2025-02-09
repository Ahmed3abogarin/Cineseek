package com.movies.cinemix.presentation.home

import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.domain.model.Result

data class HomeViewState(
    val isLoading: Boolean = false,
    val nowPlayingMovies: Movie? = null,
    val error: String? = null
)
