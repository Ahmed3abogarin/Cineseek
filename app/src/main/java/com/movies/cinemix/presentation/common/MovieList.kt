package com.movies.cinemix.presentation.common

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.MovieDetails
import com.movies.cinemix.domain.model.Cast
import com.movies.cinemix.domain.model.Movie
import com.movies.cinemix.ui.theme.Gold
import com.movies.cinemix.ui.theme.MyColor
import com.movies.cinemix.ui.theme.MyColor2
import com.movies.cinemix.ui.theme.MyRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.util.Locale
import kotlin.math.abs

@Composable
fun MovieList(
    moviesList: LazyPagingItems<Movie>,
    onClick: (Int) -> Unit,
) {
    val handlePagingResult = handlePagingResult(movies = moviesList)
    val visibleMovies = minOf(20, moviesList.itemCount)
    if (handlePagingResult) {
        LazyRow {
            items(visibleMovies) {
                moviesList[it]?.let { movie ->
                    val currentMovie = moviesList[it]
                    Column {
                        MovieCard(movie, onClick = { onClick(currentMovie!!.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun SliderList(movies: LazyPagingItems<Movie>, onClick: (Int) -> Unit) {
    val handlePagingResult = handlePagingResult(movies = movies, num = 3)
    if (handlePagingResult) {
        val pagerState = rememberPagerState(
            pageCount = { movies.itemCount },
            initialPage = 1
        )
        val context = LocalContext.current
        val colors = listOf(
            Color.Black,
            Color.Black.copy(alpha = .7f),
            Color.Transparent
        ).reversed()




        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(2000)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                    animationSpec = tween(600)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(end = 40.dp, start = 40.dp)
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val fraction = 1f - abs(pageOffset).coerceIn(0f, 1f)


                Card(modifier = Modifier.graphicsLayer {
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = fraction
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = fraction
                    )

                }) {
                    Box(modifier = Modifier.clickable { onClick(movies[page]!!.id) }) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data("https://image.tmdb.org/t/p/w500/" + movies[page]!!.backdrop_path)
                                .build(),
                            contentDescription = "",
                            modifier = Modifier
                                .height(200.dp)
                                .width(356.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier
                                .background(brush = Brush.verticalGradient(colors))
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier.weight(1f) // Allows Text to take up available space
                            ) {
                                Text(
                                    text = movies[page]!!.title,
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Button(
                                onClick = {
                                    onClick(movies[page]!!.id)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MyRed
                                )
                            ) {
                                Text(text = "See more")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridMoviesList(
    movies: LazyPagingItems<Movie>,
    navigateToDetails: (Movie) -> Unit,
) {
    val handlePagingResult = handlePagingResult(movies = movies, num = 2)
    if (handlePagingResult) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp)
                .background(
                    MyColor
                ),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(movies.itemCount) { page ->
                movies[page]?.let { movie ->
                    MovieCard2(movie, onClick = { navigateToDetails(movies[page]!!) })
                }
            }
        }


    }
}

@Composable
fun CastList(cast: List<Cast>, navigateToCastDetails: (Int) -> Unit) {

    val context = LocalContext.current
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(cast) { person ->
            Column(modifier = Modifier
                .width(74.dp)
                .clickable(
                    onClick = {
                        navigateToCastDetails(person.id)
                    }
                )) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500/" + person.profile_path)
                        .placeholder(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(74.dp)
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


@Composable
fun handlePagingResult(
    movies: LazyPagingItems<Movie>,
    num: Int = 1,
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
                    GirdEffect()
                }

                3 -> {
                    SliderEffect()
                }

            }

            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        movies.itemCount == 0 -> {
            EmptyScreen(prompt = "Nothing found")
            false
        }

        else -> {
            true
        }

    }


}

@Composable
fun MovieCard(movie: Movie, modifier: Modifier = Modifier, onClick: () -> Unit) {
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
                            .placeholder(R.drawable.place_holder)
                            .error(R.drawable.place_holder)
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
                    Text(text = "%.1f".format(Locale.US,movie.vote_average), color = Gold, fontSize = 12.sp)
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
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun LastMovieCard(movie: MovieDetails, modifier: Modifier = Modifier, onClick: () -> Unit) {
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
                            .placeholder(R.drawable.place_holder)
                            .error(R.drawable.place_holder)
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
                    Text(text = "%.1f".format(Locale.US,movie.vote_average), color = Gold, fontSize = 12.sp)
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
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MovieCard2(movie: Movie, modifier: Modifier = Modifier, onClick: () -> Unit) {
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
                            .crossfade(true)
                            .error(R.drawable.place_holder)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(2f / 3f)
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
                    Text(text = "%.1f".format(Locale.US,movie.vote_average), color = Gold, fontSize = 12.sp)
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