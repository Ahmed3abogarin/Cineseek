package com.movies.cinemix.presentation

import com.movies.cinemix.domain.model.MovieResponse
import com.movies.cinemix.domain.model.PersonResponse

data class CastState(
    val personInfo: PersonResponse? = null,
    val personMovies: MovieResponse? = null
)