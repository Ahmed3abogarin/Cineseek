package com.movies.cinemix.presentation.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.R
import com.movies.cinemix.presentation.common.GenreList
import com.movies.cinemix.presentation.common.LastMovieCard
import com.movies.cinemix.presentation.common.MovieList
import com.movies.cinemix.presentation.common.SliderList
import com.movies.cinemix.ui.theme.HeaderColor
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyRed
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeState,
    navigateToAll: (String) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    val coroutine = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .background(MyColor)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HeaderColor)
                .padding(top = 12.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            IconButton(onClick = {
                coroutine.launch {
                    scrollState.animateScrollTo(
                        scrollState.maxValue, animationSpec = tween(
                            durationMillis = 1500,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "", tint = Color.White)
            }
            Image(painter = painterResource(R.drawable.app_logo), contentDescription = "App logo")
            IconButton(onClick = { navigateToSearch() }) {
                Icon(Icons.Default.Search, contentDescription = "", tint = Color.White)
            }

        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            thickness = 0.2.dp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Trending this week ",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Image(
                painter = painterResource(R.drawable.fire2), contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        state.trendWeek?.let {
            val trendMovies = state.trendWeek.collectAsLazyPagingItems()
            SliderList(movies = trendMovies, onClick = { navigateToDetails(it) })
        }
        state.lastMovies?.let {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(4.dp)
                            .background(MyRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Last viewed",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(it, key = { it.id}) { movie ->
                    LastMovieCard(movie, modifier = Modifier.animateItem()){navigateToDetails(movie.id)}
                }
            }
        }


        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Now playing",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("nowPlaying")
                })
            )
        }
        state.nowPlaying?.let {
            MovieList(
                moviesList = state.nowPlaying.collectAsLazyPagingItems(),
                onClick = { navigateToDetails(it) })
        }



        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "What's Popular",
                    style = MaterialTheme.typography.titleLarge
                )

            }

            Text(
                "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("popular")

                })
            )
        }

        state.popularMovies?.let {
            MovieList(
                moviesList = state.popularMovies.collectAsLazyPagingItems(),
                onClick = { navigateToDetails(it) }
            )
        }




        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Upcoming movies",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                text = "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("upcoming")

                })
            )
        }

        state.upcomingMovies?.let {
            MovieList(
                state.upcomingMovies.collectAsLazyPagingItems(),
                onClick = { navigateToDetails(it) })
        }



        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Top rated",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("topRated")

                })
            )
        }
        state.topRatedMovies?.let {
            MovieList(
                state.topRatedMovies.collectAsLazyPagingItems(),
                onClick = { navigateToDetails(it) }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Arabic Movies",
                    style = MaterialTheme.typography.titleLarge
                )
            }


            Text(
                "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("arabic")

                })
            )
        }
        state.arabicMovies?.let {
            MovieList(
                state.arabicMovies.collectAsLazyPagingItems(),
                onClick = { navigateToDetails(it) })
        }


        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(4.dp)
                        .background(MyRed)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Marvel Movies",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Text(
                "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("marvel")

                })
            )
        }
        state.marvelMovies?.let {
            MovieList(
                state.marvelMovies.collectAsLazyPagingItems(),
                onClick = { navigateToDetails(it) }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(4.dp)
                    .background(MyRed)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Discover by genre",
                style = MaterialTheme.typography.titleLarge
            )
        }

        GenreList(navigateToGenre = { navigateToAll(it) })
        Spacer(
            modifier = Modifier
                .navigationBarsPadding()
                .height(110.dp)
        )
    }
}


@Preview
@Composable
fun NowPlayingPreview() {

    // using view model in preview cause the preview not to showing so don't forget not to use viewmodel in preview
    //val viewmodel: HomeViewModel = hiltViewModel()

}


