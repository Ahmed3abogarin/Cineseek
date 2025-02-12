package com.movies.cinemix.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState

@Composable
fun EmptyScreen(modifier: Modifier = Modifier, error: LoadState.Error? = null) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Text(text = "THERE ARE Something :))))")
    }

}