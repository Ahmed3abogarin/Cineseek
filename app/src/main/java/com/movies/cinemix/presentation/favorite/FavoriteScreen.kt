package com.movies.cinemix.presentation.favorite

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.MovieList

@Composable
fun FavoriteScreen(
    state: FavoriteState,
    onClick: (Movies) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(text = " Favorite screen")

        if(state.favoriteMovies.isNotEmpty()){
            Log.v("MOVIES",state.favoriteMovies[0].toString())
            Log.v("MOVIES","size: "+state.favoriteMovies.size.toString())
        }


        MovieList(moviesList = state.favoriteMovies, onClick = {onClick(it)})
    }
}




