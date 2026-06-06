package com.movies.cinemix.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Constants {
    const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/"
    const val APP_ENTRY = "appEntry"

    private val gradientColors = listOf(
        Color.Black,
        Color.Black.copy(alpha = .7f),
        Color.Transparent
    ).reversed()

    val gradientBackground = Brush.verticalGradient(gradientColors)



}