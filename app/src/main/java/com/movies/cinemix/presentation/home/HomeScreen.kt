package com.movies.cinemix.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.movies.cinemix.presentation.common.YoutubePlayer

@Composable
fun HomeScreen(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
) {
    val viewmodel: HomeViewModel = hiltViewModel()
    val state by viewmodel.state.collectAsState()
   // YoutubeButton(videoId,lifecycleOwner)
    NowPlayingMovies(state)

}

@Composable
fun NowPlayingMovies(
    state: HomeViewState
){
    if (state.isLoading){
        CircularProgressIndicator()
    }
    if (state.nowPlayingMovies != null){
        Column {
            LazyColumn {
                items(state.nowPlayingMovies.results) { movie ->
                    Text(text = movie.title)
                }

            }
        }
    }



}




// TODO : Temporary
@Composable
fun YoutubeButton(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
) {
    var showDialog by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(
            onClick = { showDialog = true }
        ) {
            Text(text = "Display youtube pop up window", fontSize = 22.sp)
        }
        if (showDialog) {
            YoutubePlayer(videoId, lifecycleOwner, onDismiss = {
                showDialog = false
            })
        }
    }

}
