package com.movies.cinemix.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.domain.model.Movies
import com.movies.cinemix.presentation.common.MovieList
import com.movies.cinemix.presentation.common.MySearchBar
import com.movies.cinemix.presentation.seeall.MovieCard2
import com.movies.cinemix.ui.theme.MyColor


@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Movies) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {

        MySearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(searchQuery = it)) },
            onSearch = { event(SearchEvent.SearchMovie) }
        )

        Spacer(modifier = Modifier.height(20.dp))
        state.moviesList?.let { movieList ->
            val movies = movieList.collectAsLazyPagingItems()


            Box(modifier = Modifier
                .background(Color.Magenta)
                .fillMaxSize()) {
                Spacer(modifier = Modifier.height(50.dp))
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp, bottom = 30.dp)
                        .background(
                            MyColor
                        ),
                    columns = StaggeredGridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(movies.itemCount) { page ->
                        movies[page]?.let { movie ->
                            MovieCard2(movie, onClick = {navigateToDetails(movies[page]!!)})
                        }
                    }
                }
            }





            MovieList(moviesList = movies, onClick = {})

        }

    }
}