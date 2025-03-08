package com.movies.cinemix.presentation.details

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.BackArrow
import com.movies.cinemix.presentation.common.CastList
import com.movies.cinemix.presentation.common.MovieButton
import com.movies.cinemix.presentation.common.YoutubePlayer
import com.movies.cinemix.ui.theme.BottomColor
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun DetailsScreen(
    movie: Movies,
    detailsViewModel: DetailsViewModel,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    lifecycleOwner: LifecycleOwner,
    navigateToCastDetails: (Int) -> Unit,

    ) {
    val context = LocalContext.current
    val state = detailsViewModel.state.value

    LaunchedEffect(false) {
        event(DetailsEvent.UpdateMovieId(movieId = movie.id))
        event(DetailsEvent.UpdateMovieGenre(genres = movie.genre_ids))

    }
    event(DetailsEvent.CheckSaveStatus(movie.id))
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog && state.movieKey != null) {
        YoutubePlayer(state.movieKey.toString(), lifecycleOwner, onDismiss = {
            showDialog = false
        })
    }





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
                    .error(R.drawable.place_holder)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            BackArrow(navigateUp = navigateUp)
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
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(end = 12.dp, top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = movie.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        IconButton(onClick = {
                            event(DetailsEvent.SaveDeleteMovie(movie))
                        }) {
//                            painter = painterResource(if (state.savedStatus)R.drawable.saved_filled else R.drawable.bookmark)
                            Icon(
                                if (state.savedStatus) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(42.dp)
                                    .padding(end = 5.dp, bottom = 7.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                Intent(Intent.ACTION_SEND).also {
                                    it.putExtra(
                                        Intent.EXTRA_TEXT,
                                        "https://www.youtube.com/watch?v=${state.movieKey}"
                                    )
                                    it.type = "text/plain"
                                    if (it.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(it)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Share, contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(42.dp)
                                    .padding(end = 5.dp, bottom = 7.dp)
                            )
                        }

                    }


//                    Spacer(modifier = Modifier.height(10.dp))
                    if (state.genres.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(
                                6.dp,
                                Alignment.CenterHorizontally
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(state.genres) { genre ->

                                Text(
                                    text = genre,
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .clip(
                                            RoundedCornerShape(30.dp)
                                        )
                                        .background(BottomColor)
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
                            style = MaterialTheme.typography.bodyLarge.copy(Color.White)
                        )

                        Text(
                            text = "IMDB ${"%.1f".format(movie.vote_average)}",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Gold)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Text("Summery", style = MaterialTheme.typography.titleMedium.copy(Color.White))

                    Spacer(modifier = Modifier.height(5.dp))

                    Column(
                        modifier = Modifier
                            .height(115.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = movie.overview,
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
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
                            navigateToCastDetails = { navigateToCastDetails(it) })

                    }

                }
            }

            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                MovieButton(onClick = { showDialog = true })
            }

        }


    }
}
