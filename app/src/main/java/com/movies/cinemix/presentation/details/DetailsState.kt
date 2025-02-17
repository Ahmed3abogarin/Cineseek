package com.movies.cinemix.presentation.details

import com.movies.cinemix.domain.model.CastResponse

data class DetailsState(
    val movieId: Int? = null,
    val castList: CastResponse? = null,
    val showDialog: Boolean = false,
    val movieKey: String? = null
)