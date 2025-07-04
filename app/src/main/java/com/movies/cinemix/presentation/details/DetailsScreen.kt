package com.movies.cinemix.presentation.details

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.presentation.common.CastList
import com.movies.cinemix.presentation.common.MovieBackArrow
import com.movies.cinemix.presentation.common.MovieButton
import com.movies.cinemix.presentation.common.MovieYouTubePlayer
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyColor
import java.util.Locale

@Composable
fun DetailsScreen(
    state: DetailsState,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    navigateToCastDetails: (Int) -> Unit,
) {

    val context = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var isFullScreen by rememberSaveable { mutableStateOf(false) }
    var currentSecond by rememberSaveable { mutableFloatStateOf(0f) }


    state.movie?.let {
        val movie = it
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MyColor)
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(.65f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                        .placeholder(R.drawable.place_holder)
                        .crossfade(true)
                        .error(R.drawable.place_holder)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                MovieBackArrow(navigateUp = navigateUp)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(top = 190.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(520.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(Color.Black.copy(alpha = .68f))
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = movie.title,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            IconButton(
                                modifier = Modifier
                                    .padding(end = 5.dp, bottom = 7.dp)
                                    .size(42.dp),
                                onClick = {
                                    event(DetailsEvent.SaveDeleteMovie(movie))
                                }) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    imageVector = if (state.savedStatus) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = {
                                    Intent(Intent.ACTION_SEND).also { key ->
                                        key.putExtra(
                                            Intent.EXTRA_TEXT,
                                            "https://www.youtube.com/watch?v=${state.movieKey}"
                                        )
                                        key.type = "text/plain"
                                        if (key.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(key)
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Share, contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .padding(bottom = 7.dp)
                                )
                            }

                        }


//                    Spacer(modifier = Modifier.height(10.dp))
                        if (state.movie.genres.isNotEmpty()) {
                            val genres = state.movie.genres
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(
                                    6.dp,
                                    Alignment.CenterHorizontally
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(genres) { genre ->
                                    Text(
                                        text = genre.name,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .border(
                                                width = 0.7.dp,
                                                color = Color.White,
                                                shape = CircleShape
                                            )
                                            .background(Color.Black.copy(alpha = 0.7f))
                                            .padding(start = 8.dp, end = 8.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.8.dp)
                                .background(
                                    Color.White
                                )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = movie.release_date,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                text = "IMDB ${"%.1f".format(Locale.US, movie.vote_average)}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = Gold,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))


                        Text(
                            "Summery",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Column(
                            modifier = Modifier
                                .height(115.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = movie.overview,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }


                        Spacer(modifier = Modifier.height(10.dp))
                        if (state.castList != null) {
                            Text(
                                text = "Cast",
                                style = MaterialTheme.typography.titleMedium.copy(Color.White)
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            CastList(
                                cast = state.castList.cast,
                                navigateToCastDetails = { movie -> navigateToCastDetails(movie) })

                        }

                    }
                }

                Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                    MovieButton(onClick = {
                        if (state.movieKey == null) {
                            event(DetailsEvent.CheckTrailerStatus)
                        }
                        showDialog = true
                    })
                }

            }

            if (showDialog && state.movieKey != null) {
                MovieYouTubePlayer(
                    videoId = state.movieKey,
                    currentSecond = currentSecond,
                    isFullscreen = isFullScreen,
                    onDismiss = { showDialog = false },
                    onBackPress = { isFullScreen = false },
                    updateSecond = { newSec -> currentSecond = newSec },
                    toggleFullscreen = { isFullScreen = !isFullScreen }
                )
            }
        }

    }
}

