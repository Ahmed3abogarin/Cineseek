package com.movies.cinemix.presentation.seeall

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.GridMoviesList
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyColor2


@Composable
fun SeeAllMovies(
    viewModel: SeeAllViewModel,
    navigateToDetails: (Movies) -> Unit,
    navigateUp: () -> Unit,
) {


    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MyColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 3.dp)
        ) {
            IconButton(
                onClick = { navigateUp() },
                Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(62.dp)
                )
            }
        }
        state.movies?.let {
            val movies = state.movies.collectAsLazyPagingItems()
            GridMoviesList(
                movies,
                navigateToDetails = { navigateToDetails(it) },
            )

        }
    }


}

@Composable
fun MovieCard2(movie: Movies, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier
        .background(MyColor)
        .clickable { onClick() }) {

        Box {
            Card(
                modifier = modifier
                    .height(271.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.background(MyColor2)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                            .placeholder(R.drawable.place_holder)
                            .error(R.drawable.place_holder)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(271.dp)
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



        Box(
            modifier = Modifier
                .width(200.dp)
                .padding(start = 5.dp, top = 3.dp)
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
        Spacer(modifier = Modifier.height(24.dp))
    }
}
