package com.movies.cinemix.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.movies.cinemix.presentation.common.EmptySearch
import com.movies.cinemix.presentation.common.GridMoviesList
import com.movies.cinemix.presentation.common.MySearchBar
import com.movies.cinemix.ui.theme.MyColor


@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .background(MyColor)
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, end = 16.dp)
    ) {

        MySearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(searchQuery = it)) },
            onSearch = { event(SearchEvent.SearchMovie) }
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (state.moviesList == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                EmptySearch()
            }
        }
        state.moviesList?.let { movieList ->
            val movies = movieList.collectAsLazyPagingItems()
            GridMoviesList(movies = movies, navigateToDetails = {navigateToDetails(it.id)})
        }

    }
}