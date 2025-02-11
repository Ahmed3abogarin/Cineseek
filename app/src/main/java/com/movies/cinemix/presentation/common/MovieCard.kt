package com.movies.cinemix.presentation.common

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.movies.cinemix.domain.model.Result
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun MovieCard( movies: List<Result>) {
    val context = LocalContext.current
    LazyRow {
        items(movies){ movie ->
            Card(
                modifier = Modifier.background(MyColor)
                    .height(320.dp)
                    .width(153.dp)
                    .padding(3.dp)
                    ,
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column (modifier = Modifier.background(MyColor).fillMaxSize()){
                    AsyncImage(
                        model = ImageRequest.Builder(context).data("https://image.tmdb.org/t/p/w500/"+movie.poster_path).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(231.dp)
                            .fillMaxWidth()
                        ,
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = movie.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                }
            }
        }
    }

}