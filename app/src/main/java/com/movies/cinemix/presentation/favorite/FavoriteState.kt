package com.movies.cinemix.presentation.favorite

import com.movies.cinemix.SingleMovie

data class FavoriteState(
    val favoriteMovies: List<SingleMovie> = emptyList()
)