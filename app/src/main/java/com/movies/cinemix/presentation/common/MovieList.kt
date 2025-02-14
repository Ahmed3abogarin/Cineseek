package com.movies.cinemix.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.MyColor2

@Composable
fun MovieList(moviesList: LazyPagingItems<Movies>) {
    val context = LocalContext.current
    val handlePagingResult = handlePagingResult(movies = moviesList)
    if (handlePagingResult) {
        LazyRow {
            items(moviesList.itemCount) {
                moviesList[it]?.let { movie ->
                    Column {
                        Card(
                            modifier = Modifier
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
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(modifier = Modifier.width(140.dp).padding(start = 5.dp)){
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

            }
        }
    }

}

@Composable
fun handlePagingResult(
    movies: LazyPagingItems<Movies>,
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
            MyEffect()
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