package com.movies.cinemix.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastMovies(
    @PrimaryKey
    val movieId: Int
)
