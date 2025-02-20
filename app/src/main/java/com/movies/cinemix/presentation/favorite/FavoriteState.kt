package com.movies.cinemix.presentation.favorite

import com.movies.cinemix.domain.model.Movies

data class FavoriteState(
    val favoriteMovies: List<Movies> = emptyList()
)