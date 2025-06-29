package com.movies.cinemix.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MovieBackArrow(navigateUp: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { navigateUp() },
        modifier = modifier.padding(top = 32.dp, start = 16.dp)
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp)
                .background(Color.Black.copy(alpha = 0.25f))
                .fillMaxSize()
        )

    }
}
