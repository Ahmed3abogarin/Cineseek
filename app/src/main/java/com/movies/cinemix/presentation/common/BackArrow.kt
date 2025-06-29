package com.movies.cinemix.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.movies.cinemix.ui.theme.BorderColor
import com.movies.cinemix.ui.theme.BoxColor

@Composable
fun BackArrow(modifier: Modifier = Modifier,navigateUp : () -> Unit){
    IconButton(
        modifier = modifier
            .padding(start = 16.dp)
            .size(64.dp)
            .border(width = 1.dp, color = BorderColor, shape = CircleShape)
            .shadow(elevation = 8.dp, spotColor = Color.White, shape = CircleShape)
            .background(BoxColor, shape = CircleShape),
        onClick = { navigateUp() }) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "",
            tint = Color.White
        )
    }
}