package com.movies.cinemix.presentation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.movies.cinemix.domain.model.Movies

@Composable
fun RandomMoviePickerScreen(viewModel: PickerViewModel) {
    val context = LocalContext.current
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        AnimatedContent(
            targetState = state,
            transitionSpec = { fadeIn() + scaleIn() togetherWith  fadeOut() + scaleOut() }
        ) { movieData ->
            movieData?.let {
                Log.v("TTTTOO",it.title)

                MovieCard(movie = it)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        IconButton(onClick = {
            viewModel.getRandomMovies()
        }) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Shuffle",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}



@Composable
fun MovieCard(movie: Movies) {
    Card (
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth(0.85f)
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Text(
                text = movie.title,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}
