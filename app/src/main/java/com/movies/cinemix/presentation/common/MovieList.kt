package com.movies.cinemix.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyColor2

@Composable
fun MovieList(
    moviesList: LazyPagingItems<Movies>,
    onClick: (Movies) -> Unit,
) {
    val handlePagingResult = handlePagingResult(movies = moviesList)
    val visibleMovies = minOf(20, moviesList.itemCount)
    if (handlePagingResult) {
        LazyRow {
            items(visibleMovies) {
                moviesList[it]?.let { movie ->
                    val currentMovie = moviesList[it]
                    Column {
                        MovieCard(movie, onClick = { onClick(currentMovie!!) })

                    }
                }

            }
        }
    }

}


@Composable
fun MovieList(
    moviesList: List<Movies>,
    onClick: (Movies) -> Unit,
) {

    if (moviesList.isEmpty()) {
        EmptyScreen()
    }

    LazyRow {
        items(moviesList.size) {
            moviesList[it].let { movie ->
                val currentMovie = moviesList[it]
                Column {
                    MovieCard(movie, onClick = { onClick(currentMovie) })

                }
            }

        }
    }


}


@Composable
fun handlePagingResult(
    movies: LazyPagingItems<Movies>,
    num: Int = 2,
): Boolean {
    val loadState = movies.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            when (num) {
                1 -> {
                    MyEffect()
                }

                2 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

            }

            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        movies.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }

    }


}

@Composable
fun MovieCard(movie: Movies, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier.clickable { onClick() }) {
        Box {

            Card(
                modifier = modifier
                    .height(231.dp)
                    .width(160.dp)
                    .padding(3.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.background(MyColor2)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(231.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Box(
                Modifier
                    .padding(top = 10.dp, start = 10.dp)
                    .align(Alignment.TopStart)
                    .shadow(2.dp, shape = RoundedCornerShape(30.dp))
                    .background(Color.Black.copy(alpha = .5f)), contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.padding(start = 4.dp, end = 4.dp)) {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = null,
                        tint = Gold,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 6.dp)
                    )
                    Text(text = "%.1f".format(movie.vote_average), color = Gold, fontSize = 12.sp)
                }
            }
        }


        Spacer(modifier = Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .width(140.dp)
                .padding(start = 5.dp)
        ) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = movie.title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


    }

}