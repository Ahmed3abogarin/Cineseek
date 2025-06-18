package com.movies.cinemix.presentation.details

import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.model.CastResponse

data class DetailsState(
    val movie: MovieDetails? =null,
    val castList: CastResponse? = null,
    val showDialog: Boolean = false,
    val movieKey: String? = null,
    val savedStatus: Boolean = false
)