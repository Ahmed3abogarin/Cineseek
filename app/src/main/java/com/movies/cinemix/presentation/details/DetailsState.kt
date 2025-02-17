package com.movies.cinemix.presentation.details

import com.movies.cinemix.domain.model.CastResponse

data class DetailsState(
    val castList: CastResponse? = null,
    val showDialog: Boolean = false,
    val movieKey: String? = null,
    val genres: List<String> = emptyList()
)