package com.movies.cinemix.presentation.favorite

import com.movies.cinemix.domain.model.MovieDetails

data class FavoriteState(
    val favoriteMovies: List<MovieDetails> = emptyList()
)