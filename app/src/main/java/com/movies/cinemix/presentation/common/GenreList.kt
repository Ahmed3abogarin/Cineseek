package com.movies.cinemix.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.movies.cinemix.R
import com.movies.cinemix.domain.model.GenreItem
import com.movies.cinemix.domain.model.genres


@Composable
fun GenreList(navigateToGenre: (String) -> Unit) {
    LazyRow {
        items(genres.size) {
            GenreCard(genres[it], onClick = { navigateToGenre(genres[it].genre) })
        }
    }
}

@Composable
fun GenreCard(item: GenreItem, onClick: () -> Unit) {
    val colors = listOf(
        Color.Black,
        Color.Black.copy(alpha = .7f),
        Color.Transparent
    ).reversed()
    Box(
        modifier = Modifier
            .height(231.dp)
            .width(160.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(item.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(50.dp)
                .background(brush = Brush.verticalGradient(colors))
        )


        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(4.dp),
            text = item.genre,
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
        )


    }

}

@Preview
@Composable
fun GenreCardPreview() {
    GenreCard(
        item = GenreItem(
            image = R.drawable.crime,
            genre = "Action"
        ),
        onClick = {}
    )
}