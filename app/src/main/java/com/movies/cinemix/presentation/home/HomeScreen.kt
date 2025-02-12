package com.movies.cinemix.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.presentation.common.MovieList
import com.movies.cinemix.presentation.common.YoutubePlayer
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun HomeScreen(
    videoId: String,
    lifecycleOwner: LifecycleOwner,
) {
    val viewmodel: HomeViewModel = hiltViewModel()
    val state by viewmodel.state.collectAsState()
    // YoutubeButton(videoId,lifecycleOwner)
    NowPlayingMovies(state, viewmodel)

}

@Composable
fun NowPlayingMovies(
    state: HomeViewState,
    viewmodel: HomeViewModel,
) {
    val scrollState = rememberScrollState()
    if (state.isLoading) {
        CircularProgressIndicator()
    }
    val moviesNow = viewmodel.nowPlayingMovies.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .background(MyColor)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        ViewPagerSlider(
            pagesCount = moviesNow.itemCount,
            list = moviesNow
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Popular Movies",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text("See all", color = Color.White, fontSize = 14.sp)
        }

        MovieList(moviesList = moviesNow)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Upcoming movies",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "See all", color = Color.White, fontSize = 14.sp)
        }

        MovieList(moviesNow)

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            // space Between
            // SpaceAround

            Text(
                text = "Popular Movies",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text("See all", color = Color.White, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        MovieList(moviesNow)
    }
}


@Preview
@Composable
fun NowPlayingPreview() {

    // using view model in preview cause the preview not to showing so don't forget not to use viewmodel in preview
    //val viewmodel: HomeViewModel = hiltViewModel()

    val state = HomeViewState(isLoading = false, null)
    CinemixTheme {

//        NowPlayingMovies(state = state)
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
