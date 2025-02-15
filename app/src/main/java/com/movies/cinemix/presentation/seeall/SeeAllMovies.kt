package com.movies.cinemix.presentation.seeall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.MovieCard
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyColor2


@Composable
fun SeeAllMovies(
    movieCategory: String,
    viewModel: SeeAllViewModel,
) {



    val moviesList = viewModel.getMovies(movieCategory).collectAsLazyPagingItems()
    Scaffold (modifier = Modifier.background(Color.Magenta).fillMaxSize()) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding()).background(
                MyColor),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
            items(moviesList.itemCount) { page ->
                moviesList[page]?.let { movie ->
                    MovieCard2(movie)
                }
            }
        }
    }
}

@Composable
fun MovieCard2(movie: Movies, modifier: Modifier = Modifier){
    val context = LocalContext.current

    Column(modifier = Modifier.background(MyColor) ){
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
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(271.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(7.dp))
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
