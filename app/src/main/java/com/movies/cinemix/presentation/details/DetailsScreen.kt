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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.ui.theme.CinemixTheme
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyRed

@Composable
fun DetailsScreen(
    movie: Movies,
) {
    val context = LocalContext.current



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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(520.dp)
                .padding(start = 16.dp, end = 16.dp, top = 100.dp)
                .align(Alignment.Center)
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(20.dp))
                .background(Color.Black.copy(alpha = .4f))


        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
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
                        .height(1.dp) // Maximum thickness in the middle
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent, // Start with transparent (thin)
                                    Color.White,       // Middle with white (thickest point)
                                    Color.Transparent  // End with transparent (thin)
                                )
                            )
                        )
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${movie.release_date} - 2h 33m", style = MaterialTheme.typography.bodyLarge.copy(Color.White))

                    Text(text = "IMDB ${movie.vote_average}", style = MaterialTheme.typography.bodyLarge.copy(Color.White))
                }
                Spacer(modifier = Modifier.height(20.dp))


                Text("Summery", style = MaterialTheme.typography.titleMedium.copy(Color.White))

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = movie.overview,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )



            }


        }



        Row (modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 142.dp)){
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MyRed
                    )) {
                Text(text = "Watch Trailer")
            }
        }


    }
}


@Preview
@Composable
fun DetailsPreView() {
    CinemixTheme {
        DetailsScreen(
            movie = Movies(
                true,
                "",
                listOf(1, 2),
                2,
                "",
                "",
                "A group of words or expressions that convey little to no meaningful content or value, often used excessively or inappropriately in communication, rendering them ineffective in conveying ideas or arguments.",
                2.33,
                "",
                "2024",
                "No Body",
                false,
                2.44,
                2
            )
        )
    }
}