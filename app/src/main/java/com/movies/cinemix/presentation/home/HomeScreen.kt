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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.presentation.common.MovieList
import com.movies.cinemix.presentation.common.MySearchBar
import com.movies.cinemix.presentation.common.YoutubePlayer
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun HomeScreen(
    videoId: String,
   // lifecycleOwner: LifecycleOwner,
) {
    val viewmodel: HomeViewModel = hiltViewModel()
    // YoutubeButton(videoId,lifecycleOwner)
    HomeScreenContent(viewmodel)

}

@Composable
fun HomeScreenContent(
    viewmodel: HomeViewModel,
) {
    val scrollState = rememberScrollState()

    val moviesNow = viewmodel.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMovies = viewmodel.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = viewmodel.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewmodel.upcomingMovies.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .background(MyColor)
            .padding(top = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // I added this column to not repeat the padding for the text and the search bar
        Column(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "What would you like to watch?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontSize = 26.sp )
            )
            Spacer(modifier = Modifier.height(10.dp))
            MySearchBar(text = "", onValueChange = {}, readOnly = true, modifier = Modifier.padding(start = 16.dp, end = 16.dp)){
            }

        }
        Spacer(modifier = Modifier.height(20.dp))


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

        MovieList(moviesList = popularMovies)

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

        MovieList(upcomingMovies)

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
                text = "Top rated",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text("See all", color = Color.White, fontSize = 14.sp)
        }
        MovieList(topRatedMovies)
    }
}


@Preview
@Composable
fun NowPlayingPreview() {

    // using view model in preview cause the preview not to showing so don't forget not to use viewmodel in preview
    //val viewmodel: HomeViewModel = hiltViewModel()

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
