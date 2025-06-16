package com.movies.cinemix.presentation.seeall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.presentation.common.GridMoviesList
import com.movies.cinemix.ui.theme.BottomColor
import com.movies.cinemix.ui.theme.MyColor


@Composable
fun SeeAllMovies(
    viewModel: SeeAllViewModel,
    navigateToDetails: (Int) -> Unit,
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
                .background(BottomColor)
                .padding(top = 12.dp)
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
            Spacer(modifier = Modifier.width(14.dp))
            state.category?.let {
                Text(text = it, style = MaterialTheme.typography.titleLarge, color = Color.White)
            }
        }
        state.movies?.let {
            val movies = state.movies.collectAsLazyPagingItems()
            GridMoviesList(
                movies,
                navigateToDetails = { navigateToDetails(it.id) },
            )

        }
    }


}


