package com.movies.cinemix.presentation.search

import androidx.paging.PagingData
import com.movies.cinemix.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val moviesList: Flow<PagingData<Movie>>? = null
)