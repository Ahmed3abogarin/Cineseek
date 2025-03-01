package com.movies.cinemix.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.GenreList
import com.movies.cinemix.presentation.common.MovieList
import com.movies.cinemix.presentation.common.MySearchBar
import com.movies.cinemix.ui.theme.MyColor


@Composable
fun HomeScreen(
    navigateToAll: (String) -> Unit,
    navigateToDetails: (Movies) -> Unit,
    state: HomeState,
) {
    HomeScreenContent(
        state = state,
        navigateToAll = navigateToAll,
        navigateToDetails = navigateToDetails,
    )

}


@Composable
fun HomeScreenContent(
    state: HomeState,
    navigateToAll: (String) -> Unit,
    navigateToDetails: (Movies) -> Unit,
) {
    val scrollState = rememberScrollState()



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
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp, top = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            MySearchBar(
                text = "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
            }

        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 10.dp)
        ) {
            Text(
                text = "Trending this week ",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter = painterResource(R.drawable.fire2), contentDescription = null,
                modifier = Modifier
                    .size(28.dp)

            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        state.trendWeek?.let {
            val trendMovies = state.trendWeek.collectAsLazyPagingItems()
            ViewPagerSlider(
                pagesCount = trendMovies.itemCount,
                list = trendMovies,
                onClick = {
                    navigateToDetails(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Now playing",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
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
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "What's Popular",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
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
                onClick = { navigateToDetails(it) })
        }




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
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Top rated",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
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
                onClick = { navigateToDetails(it) })
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Arabic Movies",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "See all",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = {
                    navigateToAll("topRated")

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
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Discover by genre",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        GenreList(navigateToGenre = {})






        Spacer(modifier = Modifier.height(110.dp))
    }
}


@Preview
@Composable
fun NowPlayingPreview() {

    // using view model in preview cause the preview not to showing so don't forget not to use viewmodel in preview
    //val viewmodel: HomeViewModel = hiltViewModel()

}


