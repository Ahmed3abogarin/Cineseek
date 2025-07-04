package com.movies.cinemix.domain.model

data class PersonResponse(
    val adult: Boolean,
    val also_known_as: List<String>?,
    val biography: String?,
    val birthday: String?,
    val deathday: String,
    val gender: Int,
    val homepage: Any,
    val id: Int,
    val imdb_id: String,
    val known_for_department: String,
    val name: String,
    val place_of_birth: String,
    val popularity: Double,
    val profile_path: String
)