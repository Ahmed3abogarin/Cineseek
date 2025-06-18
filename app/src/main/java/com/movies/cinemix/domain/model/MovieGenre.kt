package com.movies.cinemix.domain.model

import androidx.annotation.DrawableRes
import com.movies.cinemix.R

data class GenreItem(
    @DrawableRes val image: Int,
    val genre: String
)

val genres = listOf(
    GenreItem(R.drawable.action,"Action"),
    GenreItem(R.drawable.advan,"Adventure"),
    GenreItem(R.drawable.family,"Family"),
    GenreItem(R.drawable.drama,"Drama"),
    GenreItem(R.drawable.animation,"Animation"),
    GenreItem(R.drawable.comedy,"Comedy"),
    GenreItem(R.drawable.romance,"Romance"),
    GenreItem(R.drawable.crime,"Crime"),
    GenreItem(R.drawable.horror,"Horror"),
    GenreItem(R.drawable.war,"War"),
    GenreItem(R.drawable.fantasy,"Fantasy"),

)
