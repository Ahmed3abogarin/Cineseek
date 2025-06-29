package com.movies.cinemix.presentation.seeall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.presentation.common.BackArrow
import com.movies.cinemix.presentation.common.GridMoviesList
import com.movies.cinemix.ui.theme.BorderColor
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp),
        ) {
            BackArrow(modifier = Modifier.align(Alignment.CenterStart), navigateUp)

            state.category?.let {
                Text(modifier = Modifier.align(Alignment.Center), text = it, style = MaterialTheme.typography.titleLarge, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp), color = BorderColor)
        Spacer(modifier = Modifier.height(8.dp))
        state.movies?.let {
            val movies = state.movies.collectAsLazyPagingItems()
            GridMoviesList(
                movies,
                navigateToDetails = { navigateToDetails(it.id) },
            )

        }
    }


}


