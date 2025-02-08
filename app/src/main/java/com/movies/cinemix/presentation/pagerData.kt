package com.movies.cinemix.presentation

import androidx.annotation.DrawableRes
import com.movies.cinemix.R

data class pagerData(
    @DrawableRes val image : Int
)


val images = listOf(
    R.drawable.first,
    R.drawable.second,
    R.drawable.third
)
