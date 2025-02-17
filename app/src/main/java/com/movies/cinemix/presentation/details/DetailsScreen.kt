package com.movies.cinemix.presentation.details

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyRed

@Composable
fun DetailsScreen(
    movie: Movies,
    detailsViewModel: DetailsViewModel,
    event: (DetailsEvent) -> Unit
) {
    val context = LocalContext.current
    val actors = detailsViewModel.state.value.castList
    event(DetailsEvent.UpdateMovieId(movieId = movie.id))




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
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }


        Box(modifier = Modifier.fillMaxWidth().align(Alignment.Center)) {

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
                            .padding( end = 12.dp, top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            Icons.Filled.FavoriteBorder, contentDescription = null, tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.8.dp)
                            .background(
                                Color.White
                            )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${movie.release_date} - 2h 33m", style = MaterialTheme.typography.bodyLarge.copy(Color.White))

                        Text(text = "IMDB ${"%.1f".format(movie.vote_average)}", style = MaterialTheme.typography.bodyLarge.copy(Color.White))
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Text("Summery", style = MaterialTheme.typography.titleMedium.copy(Color.White))

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = movie.overview,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Cast",
                        style = MaterialTheme.typography.titleMedium.copy(Color.White)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (actors != null){
                        LazyRow(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(actors.cast){ person ->
                                Column(modifier = Modifier.width(74.dp)) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data("https://image.tmdb.org/t/p/w500/" + person.profile_path)
                                            .placeholder(R.drawable.second)
                                            .error(R.drawable.first)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier.size(74.dp)
                                            .clip(RoundedCornerShape(50.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = person.original_name,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 10.sp,
                                        color = Color.White
                                    )
                                }

                            }

                        }
                    }

                }
            }

            Row (modifier = Modifier.align(Alignment.BottomCenter)){
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MyRed
                    )) {
                    Text(text = "Watch Trailer")
                }
            }

        }







    }
}


//@Preview
//@Composable
//fun DetailsPreView() {
//    CinemixTheme {
//        DetailsScreen(
//
//        )
//    }
//}