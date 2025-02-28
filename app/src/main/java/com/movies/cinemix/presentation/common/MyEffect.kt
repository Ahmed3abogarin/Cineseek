package com.movies.cinemix.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyEffect() {
    // The one purpose of this fun is for control how the shimmer effect will be displayed
    Row {
        repeat(10) {
            MovieMyEffect(modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}