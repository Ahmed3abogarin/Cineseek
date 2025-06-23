package com.movies.cinemix.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.movies.cinemix.ui.theme.CinemixTheme

@Composable
fun MyEffect() {
    // The one purpose of this fun is for control how the shimmer effect will be displayed
    LazyRow(userScrollEnabled = false) {
        items(10) {
            MovieMyEffect(modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}

@Composable
fun GirdEffect() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(20) {
            MovieMyEffect(
                modifier = Modifier
                    .height(271.dp)
                    .width(200.dp)
            )
        }

    }


}

@Composable
fun SliderEffect() {
    Row(modifier = Modifier.wrapContentWidth(unbounded = true),horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(3) {
            MovieMyEffect(
                modifier = Modifier
                    .height(200.dp)
                    .width(356.dp)
            )
        }
    }
}


@Preview
@Composable
fun LoadingScreenPreview() {
    CinemixTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(10.dp))
            SliderEffect()
            Spacer(modifier = Modifier.height(20.dp))
            MyEffect()
            Spacer(modifier = Modifier.height(20.dp))
            MyEffect()
        }

    }
}