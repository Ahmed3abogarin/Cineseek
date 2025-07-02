package com.movies.cinemix.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.ui.theme.BottomColor
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyRed
import java.util.Locale


@Composable
fun FavoriteList(
    movies: List<MovieDetails>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        items(movies.size) {
            movies[it].let { movie ->
                val currentMovie = movies[it]
                Column {
                    FavoriteCard(movie = movie, onClick = { onClick(currentMovie.id) }, navigateToDetails = {onClick(currentMovie.id)})

                }
            }
        }
        item {
            Spacer(modifier = Modifier.navigationBarsPadding().height(110.dp))
        }
    }

}

@Composable
fun FavoriteCard(
    movie: MovieDetails,
    onClick: () -> Unit,
    navigateToDetails: () -> Unit,
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .height(190.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .clip(RoundedCornerShape(8.dp))
            .background(BottomColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .height(155.dp)
                    .width(103.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(25.dp))
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star, contentDescription = null, tint = Gold,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = "%.1f".format(Locale.US,movie.vote_average), color = Gold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(3.dp))
                Button(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(start = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MyRed),
                    onClick = {
                        navigateToDetails()
                    }
                ) {
                    Text(
                        text = "Watch Trailer",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_p),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )

                }

            }

        }


    }


}
