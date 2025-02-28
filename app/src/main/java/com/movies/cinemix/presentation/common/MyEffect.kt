package com.movies.cinemix.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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