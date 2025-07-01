package com.movies.cinemix.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["movieId"], unique = true)])
data class LastMovies(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int
)
