package com.movies.cinemix.presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.EmptyScreen
import com.movies.cinemix.presentation.common.MovieList
import com.movies.cinemix.presentation.seeall.MovieCard2
import com.movies.cinemix.ui.theme.MyColor

@Composable
fun FavoriteScreen(
    state: FavoriteState,
    onClick: (Movies) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(text = " Favorite screen")
        val movies = state.favoriteMovies

        if (movies.isEmpty()) {
            EmptyScreen()
        }

        Box(
            modifier = Modifier
                .background(Color.Magenta)
                .fillMaxSize()
        ) {

            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MyColor
                    ),
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.favoriteMovies.size) { page ->
                    MovieCard2(state.favoriteMovies[page], onClick = { onClick(movies[page]) })
                }
            }
        }

        MovieList(moviesList = state.favoriteMovies, onClick = { onClick(it) })
    }
}




