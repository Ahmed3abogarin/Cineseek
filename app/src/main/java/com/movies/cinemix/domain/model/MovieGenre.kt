package com.movies.cinemix.domain.model

import androidx.annotation.DrawableRes
import com.movies.cinemix.R

data class MovieGenre(
    @DrawableRes val image: Int,
    val genre: String
)

val genres = listOf(
    MovieGenre(R.drawable.action,"Action"),
    MovieGenre(R.drawable.advan,"Adventure"),
    MovieGenre(R.drawable.family,"Family"),
    MovieGenre(R.drawable.drama,"Drama"),
    MovieGenre(R.drawable.animation,"Animation"),
    MovieGenre(R.drawable.comedy,"Comedy"),
    MovieGenre(R.drawable.romance,"Romance"),
    MovieGenre(R.drawable.crime,"Crime"),
    MovieGenre(R.drawable.horror,"Horror"),
    MovieGenre(R.drawable.war,"War"),
    MovieGenre(R.drawable.fantasy,"Fantasy"),

)
