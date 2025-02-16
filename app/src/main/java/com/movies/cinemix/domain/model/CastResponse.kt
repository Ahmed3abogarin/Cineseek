package com.movies.cinemix.domain.model

data class CastResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)