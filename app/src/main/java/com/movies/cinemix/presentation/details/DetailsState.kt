package com.movies.cinemix.presentation.details

import com.movies.cinemix.SingleMovie
import com.movies.cinemix.domain.model.CastResponse

data class DetailsState(
    val movie: SingleMovie? =null,
    val castList: CastResponse? = null,
    val showDialog: Boolean = false,
    val movieKey: String? = null,
    val savedStatus: Boolean = false
)